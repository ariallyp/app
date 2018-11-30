package com.yuanxin.app.app.controller.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.weedfs.client.AssignParams;
import net.weedfs.client.Assignation;
import net.weedfs.client.ReplicationStrategy;
import net.weedfs.client.WeedFSClient;
import net.weedfs.client.WeedFSClientBuilder;
import net.weedfs.client.net.WriteResult;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yuanxin.app.app.dto.response.BaseResponse;
import com.yuanxin.app.app.dto.response.UploadImagesRespons;
import com.yuanxin.app.app.entity.gen.FileLinkImages;
import com.yuanxin.app.app.util.HttpUtil;
import com.yuanxin.app.app.util.ResizeImage;
import com.yuanxin.framework.logging.Logger;
import com.yuanxin.framework.logging.LoggerFactory;

@Controller
@RequestMapping(value = "/client/weedfs")
public class WeesFSController {

	/**
	 * multipartResolver
	 */
	@Resource
	private CommonsMultipartResolver multipartResolver;
	
    @Value("${fileServePath}")
    private String fileServePath;//文件服务器地址
    
    @Value("${applicationurl}")   
    private String applicationurl;
	
    /**
     * LOG
     */
    private static Logger LOG = LoggerFactory.getLogger(WeesFSController.class);
    

	@RequestMapping(value = "/uploadImage", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Object uploadImage(HttpServletRequest request,
			HttpServletResponse resp, HttpSession session) throws Exception {
		Map<String,Map<String, Object>> result = new HashMap<String,Map<String, Object>>();
		 Map<String, Object>  retData = new HashMap<String, Object>();
		// 调用服务完成上载
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (!multipartResolver.isMultipart(multipartRequest)) {
			throw new RuntimeException("未获取到上载的文件！");
		}

		// srcfname 是指 文件上传标签的 name=值
		MultiValueMap<String, MultipartFile> multfiles = multipartRequest
				.getMultiFileMap();
		if (multfiles == null || multfiles.size() <= 0) {
			throw new RuntimeException("未获取到上载的文件！");
		}

		for (String srcfname : multfiles.keySet()) {
			MultipartFile mfile = multfiles.getFirst(srcfname);
			String fileNameOld = mfile.getOriginalFilename();
			InputStream imIn= mfile.getInputStream();
			BufferedImage bImage =ImageIO.read(imIn);
			BufferedImage bImageSmall = ResizeImage.zoomImage(bImage);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ByteArrayOutputStream osBig = new ByteArrayOutputStream();
			ImageIO.write(bImageSmall, "jpg", os);
			ImageIO.write(bImage, "jpg", osBig);
			InputStream isBigStream = new ByteArrayInputStream(osBig.toByteArray());
			InputStream isSmallStream = new ByteArrayInputStream(os.toByteArray());
			String fileName = fileNameOld;
			if (fileName == null || fileName.isEmpty()) {
				continue;
			}
            //1保存文件到WeedFs
            LOG.info("%s","开始上传文件");
            URL url = new URL(fileServePath);
            WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(url).build();
            Assignation a = client.assign(new AssignParams("", ReplicationStrategy.None));
            Assignation b = client.assign(new AssignParams("", ReplicationStrategy.None));
            WriteResult rs = client.write(a.weedFSFile, a.location, isBigStream,  fileName);
            LOG.info("%s","上传文件:"+rs.getName()+"-文件ID："+rs.getFid());
           
            //生成缩略图上传
            WriteResult rs2 = client.write(b.weedFSFile, b.location, isSmallStream, fileName);
            LOG.info("%s","上传文件:"+rs2.getName()+"-文件ID："+rs2.getFid());
            retData.put("fileUrl", applicationurl.concat("/app/client/weedfs/download/").concat(rs.getFid()));
            retData.put("fileUrlSmall", applicationurl.concat("/app/client/weedfs/download/").concat(rs2.getFid()));
            result.put("responseUpload", retData);
       
		}
		result.put("responseUpload", retData);
		return result;
	}
    
  
	@RequestMapping(value="/uploadFiles",method=RequestMethod.POST)
	@ResponseBody
	  public Object uploadFiles(@RequestParam("upload") CommonsMultipartFile[] file1, HttpServletRequest request) {
		 Map<String, Object>  retData = new HashMap<String, Object>();
		 Map<String,Map<String, Object>> result = new HashMap<String,Map<String, Object>>();
		for (CommonsMultipartFile commonsMultipartFile : file1) {
	      try {
	       
	        InputStream imIn=commonsMultipartFile.getInputStream();
	        BufferedImage bImage =ImageIO.read(imIn);
			String fileName = commonsMultipartFile.getOriginalFilename();
			if (fileName == null || fileName.isEmpty()) {
				continue;
			}
         
            LOG.info("%s","开始上传文件");
            URL url = new URL(fileServePath);
            WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(url).build();
            Assignation a = client.assign(new AssignParams("", ReplicationStrategy.None));
            // System.out.println(a.weedFSFile.fid + " assigned");
            WriteResult rs = client.write(a.weedFSFile, a.location, commonsMultipartFile.getInputStream(),  commonsMultipartFile.getOriginalFilename());
            LOG.info("%s","上传文件:"+rs.getName()+"-文件ID："+rs.getFid());
           
            retData.put("fid", rs.getFid());
            retData.put("fileName", rs.getName());
            retData.put("error", null);
            retData.put("size", rs.getSize());
            retData.put("fileUrl", applicationurl.concat("/app/client/weedfs/download/").concat(rs.getFid()));
            result.put("responseUpload", retData);

	        
	      } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	    }
	    return result;
	  }
	
	
	 @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
	 @ResponseBody
	  // 这里的MultipartFile[] imgs表示前端页面上传过来的多个文件，imgs对应页面中多个file类型的input标签的name，但框架只会将一个文件封装进一个MultipartFile对象，
	  // 并不会将多个文件封装进一个MultipartFile[]数组，直接使用会报[Lorg.springframework.web.multipart.MultipartFile;.<init>()错误，
	  // 所以需要用@RequestParam校正参数（参数名与MultipartFile对象名一致），当然也可以这么写：@RequestParam("imgs") MultipartFile[] files。
	  public Object uploadPP(@RequestParam MultipartFile[] upload, HttpSession session)
	      throws Exception {
		 BaseResponse baseResponse = new BaseResponse();
		 Map<String,Map<String, Object>> result = new HashMap<String,Map<String, Object>>();
		 UploadImagesRespons resp = new UploadImagesRespons();
		 Map<String, Object>  retData = new HashMap<String, Object>();
		 String fileName="";
		 int i=0;
		 List<FileLinkImages> lImages= new ArrayList<>();
	    for (MultipartFile img : upload) {
	      if (img.getSize() > 0) {
	    	  i++;
	    	  InputStream imIn= img.getInputStream();
	    	  	fileName = img.getOriginalFilename();
				BufferedImage bImage =ImageIO.read(imIn);
				BufferedImage bImageSmall = ResizeImage.zoomImage(bImage);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ByteArrayOutputStream osBig = new ByteArrayOutputStream();
				ImageIO.write(bImageSmall, "jpg", os);
				ImageIO.write(bImage, "jpg", osBig);
				InputStream isBigStream = new ByteArrayInputStream(osBig.toByteArray());
				InputStream isSmallStream = new ByteArrayInputStream(os.toByteArray());
				
				
	            //1保存文件到WeedFs
	            LOG.info("%s","开始上传文件");
	            URL url = new URL(fileServePath);
	            WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(url).build();
	            Assignation a = client.assign(new AssignParams("", ReplicationStrategy.None));
	            Assignation b = client.assign(new AssignParams("", ReplicationStrategy.None));
	            WriteResult rs = client.write(a.weedFSFile, a.location, isBigStream,  fileName);
	            LOG.info("%s","上传文件:"+rs.getName()+"-文件ID："+rs.getFid());
	           
	            //生成缩略图上传
	            WriteResult rs2 = client.write(b.weedFSFile, b.location, isSmallStream, fileName);
	            LOG.info("%s","上传文件:"+rs2.getName()+"-文件ID："+rs2.getFid());
	            FileLinkImages fileLinkImages = new FileLinkImages();
	            fileLinkImages.setFileUrl(applicationurl.concat("/app/client/weedfs/download/").concat(rs.getFid()));
	            fileLinkImages.setSmallFileUrl(applicationurl.concat("/app/client/weedfs/download/").concat(rs2.getFid()));
	            fileLinkImages.setFileName(fileName);
	            fileLinkImages.setId(i+"");
	            fileLinkImages.setFileId(rs.getFid()+" : "+rs2.getFid());
	            lImages.add(fileLinkImages);
	        	resp.uploadImagesList=lImages;
	        	baseResponse.setErrMsg("上传成功");
	    		baseResponse.setRet(BaseResponse.RET_SUCCESS);
	    		resp.setBaseResponse(baseResponse);
	      }else {
	    	baseResponse.setErrMsg("上传失败");
	  		baseResponse.setRet(BaseResponse.RET_ERROR);
	  		resp.setBaseResponse(baseResponse);
		}
	    }
	    
	    return resp;
	  }
	
    
	@RequestMapping(value = "/upload", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Object upload(HttpServletRequest request,
			HttpServletResponse resp, HttpSession session) throws Exception {
		Map<String,Map<String, Object>> result = new HashMap<String,Map<String, Object>>();
		 Map<String, Object>  retData = new HashMap<String, Object>();
		// 调用服务完成上载
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (!multipartResolver.isMultipart(multipartRequest)) {
			throw new RuntimeException("未获取到上载的文件！");
		}

		// srcfname 是指 文件上传标签的 name=值
		MultiValueMap<String, MultipartFile> multfiles = multipartRequest
				.getMultiFileMap();
		if (multfiles == null || multfiles.size() <= 0) {
			throw new RuntimeException("未获取到上载的文件！");
		}

		for (String srcfname : multfiles.keySet()) {
			MultipartFile mfile = multfiles.getFirst(srcfname);
			String fileNameOld = mfile.getOriginalFilename();
			InputStream imIn= mfile.getInputStream();
			BufferedImage bImage =ImageIO.read(imIn);
			String fileName = fileNameOld;
			if (fileName == null || fileName.isEmpty()) {
				continue;
			}
            //1保存文件到WeedFs
            LOG.info("%s","开始上传文件");
            URL url = new URL(fileServePath);
            WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(url).build();
            Assignation a = client.assign(new AssignParams("", ReplicationStrategy.None));
            // System.out.println(a.weedFSFile.fid + " assigned");
            WriteResult rs = client.write(a.weedFSFile, a.location, mfile.getInputStream(),  mfile.getOriginalFilename());
            LOG.info("%s","上传文件:"+rs.getName()+"-文件ID："+rs.getFid());
           
            retData.put("fid", rs.getFid());
            retData.put("fileName", rs.getName());
            retData.put("error", null);
            retData.put("size", rs.getSize());
            retData.put("fileUrl", applicationurl.concat("/app/client/weedfs/download/").concat(rs.getFid()));
            result.put("responseUpload", retData);
            
            /**
             * "responseUpload": {
			        "error": null,
			        "fid": "32,025f5ff8c9d9",
			        "fileName": "epic_crop_20160331144640.jpg",
			        "fileUrl": "120.24.160.24:5084/32,025f5ff8c9d9",
			        "size": 5821
			    },

             */
		}
		result.put("responseUpload", retData);
		return result;
	}

	
    @RequestMapping("/download/{fileId}")
    public void download(@PathVariable  String fileId,HttpServletRequest request,HttpServletResponse response){
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        try {
//            WeedFSFile wfile = new WeedFSFile(fileId);
//            URL url = new URL(fileServePath);
//            WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(url).build();
//            List<Location> locations = client.lookup(wfile.getVolumeId());
//	        if (locations.size() == 0) {
//	            return;
//	        }
//
//	        InputStream is = client.read(wfile, locations.get(0));
//            //3.通过response获取ServletOutputStream对象(out)
//            OutputStream output = response.getOutputStream();
//            IOUtils.copy(is, output);
//            IOUtils.closeQuietly(is);
//            IOUtils.closeQuietly(output);
            
            String addr=fileServePath;
			try {
				addr = getdownloadurl(fileId) + "/" + fileId;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            byte[] respond = HttpUtil.doGet(addr);
    		if(respond==null){
    			return;
    		}
			OutputStream output = response.getOutputStream();
			IOUtils.copy(new ByteArrayInputStream(respond), output);
			IOUtils.closeQuietly(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	// 获取下载地址
	private String getdownloadurl(String fid) throws ParseException {
		byte[] info = HttpUtil.doGet(fileServePath + "/dir/lookup?volumeId=" + fid + "&pretty=y");
		if(info==null){
			return fileServePath;
		}
		String weedfslocation = new String(info);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(weedfslocation);
		JSONArray locations = (JSONArray) obj.get("locations");
		JSONObject locallocation = (JSONObject) locations.get(0);
		String url = locallocation.get("url").toString();
		return "http://"+url;
	}
	
}
