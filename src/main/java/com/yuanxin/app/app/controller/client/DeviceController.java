package com.yuanxin.app.app.controller.client;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.yuanxin.app.app.appobject.ApnsTokenAO;
import com.yuanxin.app.app.appobject.AppUserAO;
import com.yuanxin.app.app.appobject.AppUserAllowAO;
import com.yuanxin.app.app.appobject.ApplicationAO;
import com.yuanxin.app.app.appobject.ClientAO;
import com.yuanxin.app.app.appobject.ClientLogAO;
import com.yuanxin.app.app.appobject.ClientPluginVersionAO;
import com.yuanxin.app.app.appobject.ClientVersionAO;
import com.yuanxin.app.app.appobject.OperationAO;
import com.yuanxin.app.app.appobject.OrgAO;
import com.yuanxin.app.app.appobject.OrgUserAO;
import com.yuanxin.app.app.appobject.SkinAO;
import com.yuanxin.app.app.appobject.TenantAO;
import com.yuanxin.app.app.appobject.UserAO;
import com.yuanxin.app.app.appobject.UserUserAO;
import com.yuanxin.app.app.appobject.WizConfigAO;
import com.yuanxin.app.app.dto.model.Member;
import com.yuanxin.app.app.dto.model.Msg;
import com.yuanxin.app.app.dto.model.ObjectContentApp102.Operation;
import com.yuanxin.app.app.dto.request.AddApnsTokenRequest;
import com.yuanxin.app.app.dto.request.AddOrRemoveContactRequest;
import com.yuanxin.app.app.dto.request.BindRequest;
import com.yuanxin.app.app.dto.request.ChangeSkinRequest;
import com.yuanxin.app.app.dto.request.ChangeUserInfoRequest;
import com.yuanxin.app.app.dto.request.CheckPlugUpdateRequest;
import com.yuanxin.app.app.dto.request.CheckUpdateRequest;
import com.yuanxin.app.app.dto.request.ClientLogRequest;
import com.yuanxin.app.app.dto.request.DelApnsTokenRequest;
import com.yuanxin.app.app.dto.request.FollowAppRequest;
import com.yuanxin.app.app.dto.request.GetAppOperationListRequest;
import com.yuanxin.app.app.dto.request.GetApplicationListRequest;
import com.yuanxin.app.app.dto.request.GetMemberRequest;
import com.yuanxin.app.app.dto.request.GetOrgInfoRequest;
import com.yuanxin.app.app.dto.request.GetOrgUserListRequest;
import com.yuanxin.app.app.dto.request.LoginRequest;
import com.yuanxin.app.app.dto.request.MobilDataRequest;
import com.yuanxin.app.app.dto.request.MultiBindRequest;
import com.yuanxin.app.app.dto.request.OffLineTextRequest;
import com.yuanxin.app.app.dto.request.SaveAppUserAllowRequest;
import com.yuanxin.app.app.dto.request.SearchRequest;
import com.yuanxin.app.app.dto.request.SetUserAvatarRequest;
import com.yuanxin.app.app.dto.request.UnFollowAppRequest;
import com.yuanxin.app.app.dto.request.WizConfgRequest;
import com.yuanxin.app.app.dto.response.AddOrRemoveContactResponse;
import com.yuanxin.app.app.dto.response.BaseResponse;
import com.yuanxin.app.app.dto.response.BindResponse;
import com.yuanxin.app.app.dto.response.ChangeSkinInfoRespons;
import com.yuanxin.app.app.dto.response.ChangeUserInfoResponse;
import com.yuanxin.app.app.dto.response.CheckPlugUpdateResponse;
import com.yuanxin.app.app.dto.response.CheckUpdateResponse;
import com.yuanxin.app.app.dto.response.GetAppOperationListResponse;
import com.yuanxin.app.app.dto.response.GetApplicationListResponse;
import com.yuanxin.app.app.dto.response.GetMemberResponse;
import com.yuanxin.app.app.dto.response.GetOrgInfoResponse;
import com.yuanxin.app.app.dto.response.GetOrgUserListResponse;
import com.yuanxin.app.app.dto.response.LoginResponse;
import com.yuanxin.app.app.dto.response.OfflineResponse;
import com.yuanxin.app.app.dto.response.OrgUserAppResponse;
import com.yuanxin.app.app.dto.response.Response;
import com.yuanxin.app.app.dto.response.ResponseUpload;
import com.yuanxin.app.app.dto.response.SearchResponse;
import com.yuanxin.app.app.dto.response.WizConfigRespons;
import com.yuanxin.app.app.entity.EascParam;
import com.yuanxin.app.app.entity.gen.AppUserAllowCriteria;
import com.yuanxin.app.app.entity.gen.AppUserCriteria;
import com.yuanxin.app.app.entity.gen.ApplicationCriteria;
import com.yuanxin.app.app.entity.gen.ClientCriteria;
import com.yuanxin.app.app.entity.gen.ClientCustomizedCriteria;
import com.yuanxin.app.app.entity.gen.ClientPluginVersionCriteria;
import com.yuanxin.app.app.entity.gen.ClientVersionCriteria;
import com.yuanxin.app.app.entity.gen.ObjectClient;
import com.yuanxin.app.app.entity.gen.OperationCriteria;
import com.yuanxin.app.app.entity.gen.OrgCriteria;
import com.yuanxin.app.app.entity.gen.OrgUserCriteria;
import com.yuanxin.app.app.entity.gen.Skin;
import com.yuanxin.app.app.entity.gen.SkinCriteria;
import com.yuanxin.app.app.entity.gen.UserCriteria;
import com.yuanxin.app.app.entity.gen.UserUserCriteria;
import com.yuanxin.app.app.entity.gen.Wiztalkconfig;
import com.yuanxin.app.app.entity.gen.WiztalkconfigCriteria;
import com.yuanxin.app.app.service.IApnsTokenService;
import com.yuanxin.app.app.service.IAppUserAllowService;
import com.yuanxin.app.app.service.IAppUserService;
import com.yuanxin.app.app.service.IApplicationService;
import com.yuanxin.app.app.service.IClientLogService;
import com.yuanxin.app.app.service.IClientPluginVersionService;
import com.yuanxin.app.app.service.IClientService;
import com.yuanxin.app.app.service.IClientVersionService;
import com.yuanxin.app.app.service.IOpenfireUserSerivce;
import com.yuanxin.app.app.service.IOperationService;
import com.yuanxin.app.app.service.IOrgService;
import com.yuanxin.app.app.service.IOrgUserService;
import com.yuanxin.app.app.service.IRedisService;
import com.yuanxin.app.app.service.IRedisUtilService;
import com.yuanxin.app.app.service.ISkinService;
import com.yuanxin.app.app.service.ITenantService;
import com.yuanxin.app.app.service.IUserService;
import com.yuanxin.app.app.service.IUserUserService;
import com.yuanxin.app.app.service.IWizConfigService;
import com.yuanxin.app.app.util.HttpUtil;
import com.yuanxin.app.app.util.MD5Util;
import com.yuanxin.app.app.util.SerializeUtil;
import com.yuanxin.app.app.wsdl.util.FontImageUtil;
import com.yuanxin.app.app.wsdl.util.StringUtil;
import com.yuanxin.framework.logging.Logger;
import com.yuanxin.framework.logging.LoggerFactory;
import com.yuanxin.framework.mybatis.Page;
import com.yuanxin.framework.service.ServiceResult;

import net.weedfs.client.AssignParams;
import net.weedfs.client.Assignation;
import net.weedfs.client.ReplicationStrategy;
import net.weedfs.client.WeedFSClient;
import net.weedfs.client.WeedFSClientBuilder;
import net.weedfs.client.net.WriteResult;
import top.wiz.common.easc.EascAuthHelper;
import top.wiz.common.easc.model.dto.Result;


/**
 * Device控制器。
 * 
 */
@Api(value="client/device")  
@Controller
@RequestMapping(value = "/client/device")
public class DeviceController {

	private static Logger LOG = LoggerFactory.getLogger(DeviceController.class);
	@Resource
	private IWizConfigService wizConfigService;
	@Resource
	private ISkinService skinService;
	
	@Resource
	private ITenantService tenantService;
	@Resource
	private IUserService userService;
	@Resource
	private IOrgUserService orgUserService;
	@Resource
	private IOrgService orgService;
	@Resource
	private FreeMarkerConfig freemarkerConfig;
	@Resource
	private IRedisService redisService;
	@Resource
	private IUserUserService userUserService;
	@Resource
	private IClientVersionService clientVersionService;
	@Resource
	private IApnsTokenService apnsTokenService;
	@Resource
	private IApplicationService applicationService;
	@Resource
	private IAppUserService appUserService;
	@Resource
	private IOpenfireUserSerivce openFileService;
	@Resource
	private IOperationService operationService;
	@Resource
	private IClientPluginVersionService clientPluginVersionService;

	@Resource
	private IAppUserAllowService appUserAllowService;
	
	@Resource
	private IClientLogService clientLogService;

	@Resource
	private IClientService clientService;
	
	@Resource
	private IRedisUtilService redisUtilService;

	@Value("${fileServePath}")
	private String fileServePath;

	@Value("${fileServeuplaod_5084}")
	private String fileServeuplaod_5084;
	
	@Value("${fileServeuplaod_5083}")
	private String fileServeuplaod_5083;
	
	@Value("${fileServeDownPath}")
	private String fileServeDownPath;
	

	@Value("${rejectInternatUrl}")
	private String rejectInternatUrl;

	@Value("${rejectLanUrl}")
	private String rejectLanUrl;

	@Value("${tenantId}")
	private String needbind_tenantId;
	
	@Value("${loginAndOutUrlOpen}")
	private String loginAndOutUrlOpen;
	
	@Value("${loginAndOutUrl}")
	private String loginAndOutUrl;
	
	@Value("${redis.host}")
	private String redisHost;
	private String OPEN_CLOSE_CHAT_KEY="open_close_chat_key";
	private String OPEN_CLOSE_SKIN_KEY="open_close_skin_key";
	private String OPEN_CLOSE_KEY="open_close_key_";
	private String WZTK_LOGIN_TIMES="WZTK_LOGIN_TIMES_";
	private String WZTK_puer_offline_data="WZTK_puer_offline_data";
	private String WZTK_puer_offline_get_data="WZTK_puer_offline_get_data";
	
	
	

	public class SUFFIX {

		public final static String TENANT_SUFFIX = "@tenant";

		public final static String ORG_SUFFIX = "@org";

		public final static String QUN_SUFFIX = "@qun";

		public final static String USER_SUFFIX = "@user";

		public final static String APP_SUFFIX = "@app";
	}
	

	/**
	 * 登陆接口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/loginEasc/{userId}", method = { RequestMethod.GET })
	@ResponseBody	
    @ApiOperation(value="根据UId访问easc获取token",httpMethod="GET",notes="get easc by uid",response=Result.class)  

	public Object loginEasc(@PathVariable("userId") String userId, HttpServletRequest request) {

		Result result = new Result();
		Boolean conn=redisUtilService.exists("WZTK_easc_eascParam_map");
		if (conn == null || conn == false) {
			result.setRetcode(1000);
			result.setRetmsg("redis 获取easc 参数为空 ");
			return result;
		}
		List<String> rsmap =redisUtilService.hmget("WZTK_easc_eascParam_map","appId","domain","port","userName","pwd","tenantId","userId");
		EascParam eascParam = new EascParam(rsmap.get(0), rsmap.get(1), rsmap.get(2), rsmap.get(3), rsmap.get(4), rsmap.get(5),rsmap.get(6));
		String domain=eascParam.getDomain();
		String appId=eascParam.getAppId();
		String port=eascParam.getPort();
		result =EascAuthHelper.ssoLoginByUserId(domain, port, userId, appId);
		
		return result;
	
	}
	/**
	 * 获取组织下面的用户
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchSetUserAvatar", method = { RequestMethod.POST })
	@ResponseBody
	public Object batchSetUserAvatar(@RequestBody GetOrgUserListRequest req, Model model, HttpServletRequest request)
			throws Exception {

		LOG.info(" 批量生成默认用户的接口 ", request.getSession().getId());

		GetOrgUserListResponse resp = new GetOrgUserListResponse();
		BaseResponse baseResponse = new BaseResponse();
		String orgId = req.getOrgid();
		OrgUserCriteria example = new OrgUserCriteria();
		example.createCriteria().andOrgIdEqualTo(orgId);
		UserCriteria userCriteria = new UserCriteria();
		//userCriteria.createCriteria().andTenantIdEqualTo("04acc1cf-d838-4b84-95b5-62818876e3b9");
		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			for (UserAO u : userListRet.getData()) {
				String nickName = u.getNickName();
				if (!StringUtils.isNotBlank(u.getAvatar())) {
					if (nickName != "" || nickName != null) {
						String imgName = UUID.randomUUID() + ".png";
						String fontString = StringUtil.getCharString(nickName);
						FontImageUtil.createImage(fontString, imgName);
						InputStream inputStream = new FileInputStream("D://upload/" + imgName);
						// 调用服务完成上载
						URL url = new URL(fileServePath);
						WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(url).build();
						Assignation a = client.assign(new AssignParams("", ReplicationStrategy.None));
						String urllocation = a.location.url;
						System.err.println(urllocation);
						if (urllocation.contains("5083")) {
							a.location.publicUrl=fileServeuplaod_5083;
							a.location.url=fileServeuplaod_5083;
						}else {
							a.location.publicUrl=fileServeuplaod_5084;
							a.location.url=fileServeuplaod_5084;
						}
						WriteResult rs = client.write(a.weedFSFile, a.location, inputStream, imgName);
						LOG.info(" 文件名 ", a.weedFSFile.fid);
						u.setAvatar(rs.getFid());
						userService.update(u);
						

					}
				}

			}
		}

		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}
	
	
	/**
	 * 开放密聊接口
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openOrClose2", method = { RequestMethod.POST })
	@ResponseBody
	public Object openOrClose2(@RequestBody WizConfgRequest req, Model model, HttpServletRequest request) throws Exception {
				BaseResponse baseResponse = new BaseResponse();
				String target=req.getTarget();
				Object switchForSkin = redisUtilService.get(OPEN_CLOSE_KEY+target);
				if (switchForSkin!=null&&switchForSkin!="") {
					baseResponse.setErrMsg(switchForSkin.toString());
					baseResponse.setRet(BaseResponse.RET_SUCCESS);
					return baseResponse;
				}else {
					baseResponse.setErrMsg("获取开关失败");
					baseResponse.setRet(BaseResponse.RET_ERROR);
					return baseResponse;
			
				}
				
				
				
	}
	
	
	
	/**
	 * 开放密聊接口
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openOrClose", method = { RequestMethod.POST })
	@ResponseBody
	public Object openOrClose(@RequestBody WizConfgRequest req, Model model, HttpServletRequest request) throws Exception {
		 LOG.info("进入openOrClose接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
				BaseResponse baseResponse = new BaseResponse();
				String target=req.getTarget();
				String switchForSkin = redisUtilService.get(OPEN_CLOSE_KEY+target);
				if (switchForSkin!=null&&switchForSkin!="") {
					baseResponse.setErrMsg(switchForSkin);
					baseResponse.setRet(BaseResponse.RET_SUCCESS);
					return baseResponse;
				}else {
					baseResponse.setErrMsg("获取开关失败");
					baseResponse.setRet(BaseResponse.RET_ERROR);
					return baseResponse;
			
				}
				
				
				
	}
	
	
	
	/**
	 * WIZTALK CONFIG 
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wizConfig", method = { RequestMethod.POST })
	@ResponseBody
	public Object wizConfig(@RequestBody WizConfgRequest req, Model model, HttpServletRequest request) {
		 LOG.info("进入wizConfig接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
	
		WizConfigRespons resp = new WizConfigRespons();
		BaseResponse baseResponse = new BaseResponse();

		String target = req.getTarget();
	
		WiztalkconfigCriteria wiztalkconfigCriteria = new WiztalkconfigCriteria();
		wiztalkconfigCriteria.createCriteria().andTARGETEqualTo(target);
		ServiceResult<List<WizConfigAO>> wizConfigResult = wizConfigService.selectByCriteria(wiztalkconfigCriteria);
		List<Wiztalkconfig> menberlistInner = new ArrayList<Wiztalkconfig>();
		List<Wiztalkconfig> menberlistOut = new ArrayList<Wiztalkconfig>();

		if (wizConfigResult.isSucceed() && !CollectionUtils.isEmpty(wizConfigResult.getData())) {
				//resp.skinList=skinListRet;
			for (WizConfigAO wizConfigAO : wizConfigResult.getData()) {
				if ("内网".equals(wizConfigAO.getTYPE())) {
					menberlistInner.add(wizConfigAO);
				}else if ("外网".equals(wizConfigAO.getTYPE())) {
					menberlistOut.add(wizConfigAO);
				}
				
			}
				resp.innerWizconfigs=menberlistInner;
				resp.outWizconfigs=menberlistOut;

				baseResponse.setErrMsg("获取wiztalk 配置信息成功");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			
		}
		baseResponse.setErrMsg("获取wiztalk 配置信息失败");
		baseResponse.setRet(BaseResponse.RET_ERROR);
		resp.setBaseResponse(baseResponse);
		return resp;

	}
	
	

	/**
	 * 单点登录
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/SSObject", method = { RequestMethod.POST })
	@ResponseBody
	public Object sSObject(@RequestBody LoginRequest req, Model model, HttpServletRequest request) throws Exception{
		 LOG.info("进入SSObject接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
				String deviceId =req.getBaseRequest().getDeviceID();
				String username = req.getUserName();
				String deviceIDSSO ="";
				if (username!=null&&username!="") {
					deviceIDSSO=redisUtilService.get(username);
	
				}
				
				LOG.info("deviceId is  "+ deviceId);
				LOG.info("username is  "+ username);
				LOG.info("deviceIDSSO is  "+ deviceIDSSO);
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setErrMsg("获取单点登录信息");
				if (deviceId.equalsIgnoreCase(deviceIDSSO)) {
					baseResponse.setRet(BaseResponse.RET_SUCCESS);

				}else {
					baseResponse.setRet(1);
				}
				return baseResponse;
		
	}
	


	/**
	 * 开放皮肤接口
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openSkin", method = { RequestMethod.POST })
	@ResponseBody
	public Object openSkin( Model model, HttpServletRequest request) throws Exception {
		 LOG.info("进入openSkin接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
				BaseResponse baseResponse = new BaseResponse();
				String switchForSkin = redisUtilService.get(OPEN_CLOSE_SKIN_KEY);
				if (switchForSkin!=null&&switchForSkin!="") {
					baseResponse.setErrMsg(switchForSkin);
					baseResponse.setRet(BaseResponse.RET_SUCCESS);
					return baseResponse;
				}else {
					baseResponse.setErrMsg("获取皮肤开关失败");
					baseResponse.setRet(BaseResponse.RET_ERROR);
					return baseResponse;
			
				}
				
				
				
	}
	
	
	/**
	 * 开放密聊接口
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openChat", method = { RequestMethod.POST })
	@ResponseBody
	public Object openChat( Model model, HttpServletRequest request) throws Exception{
		 LOG.info("进入openChat接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
				BaseResponse baseResponse = new BaseResponse();
				String switchForChat = redisUtilService.get(OPEN_CLOSE_CHAT_KEY);
				if (switchForChat!=null&&switchForChat!="") {
					baseResponse.setErrMsg(switchForChat);
					baseResponse.setRet(BaseResponse.RET_SUCCESS);
					return baseResponse;
				}else {
					baseResponse.setErrMsg("获取密聊开关失败");
					baseResponse.setRet(BaseResponse.RET_ERROR);
					return baseResponse;
			
				}
	}
	
	
	
	
	
	/**
	 * 皮肤接口
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeSkin", method = { RequestMethod.POST })
	@ResponseBody
	public Object changeSkin(@RequestBody ChangeSkinRequest req, Model model, HttpServletRequest request) {
		 LOG.info("进入changeSkin接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
		ChangeSkinInfoRespons resp = new ChangeSkinInfoRespons();
		BaseResponse baseResponse = new BaseResponse();

		String deviceType = req.getBaseRequest().getDeviceType();
	
		SkinCriteria skinCriteria = new SkinCriteria();
		skinCriteria.createCriteria().andDiviceTypeEqualTo(deviceType);
		ServiceResult<List<SkinAO>> skinListRet = skinService.selectByCriteria(skinCriteria);
		List<Skin> menberlist = new ArrayList<Skin>();
		if (skinListRet.isSucceed() && !CollectionUtils.isEmpty(skinListRet.getData())) {
				//resp.skinList=skinListRet;
			for (SkinAO skin : skinListRet.getData()) {
				menberlist.add(skin);
			}
			
				resp.skinList=menberlist;
				baseResponse.setErrMsg("获取皮肤接口信息成功");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			
		}
		baseResponse.setErrMsg("获取皮肤信息失败");
		baseResponse.setRet(BaseResponse.RET_ERROR);
		resp.setBaseResponse(baseResponse);
		return resp;

	}
	
	
	
	
	

	/**
	 * 登陆接口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public Object login(@RequestBody LoginRequest req, Model model, HttpServletRequest request) {
		LOG.info("进入login接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		 request.getHeader("user-agent"));
		LOG.info("SessionId %s ：", request.getSession().getId());
		String username = req.getUserName();
		LOG.info("userName is ："+ req.getUserName());
		int timesLimit = 5;
		if (redisUtilService.exists(WZTK_LOGIN_TIMES+"TIMELIMTS")) {
			timesLimit=Integer.parseInt(redisUtilService.get(WZTK_LOGIN_TIMES+"TIMELIMTS"));
		}
		int times = 0 ;
		String redisLoginTimesKey =WZTK_LOGIN_TIMES+username;
		if (redisUtilService.exists(WZTK_LOGIN_TIMES+username)) {
			times=Integer.parseInt(redisUtilService.get(WZTK_LOGIN_TIMES+username));
		}
		int timeLimitLeng = 60*60*24;
		if (redisUtilService.exists(WZTK_LOGIN_TIMES+"TIMELIMTS_SIZE")) {
			timeLimitLeng=Integer.parseInt(redisUtilService.get(WZTK_LOGIN_TIMES+"TIMELIMTS_SIZE"));
		}
		String password = req.getPassword();
		String deviceId = req.getBaseRequest().getDeviceID();
		String deviceType = req.getBaseRequest().getDeviceType();
		LOG.info("访问设备 ：", deviceType);
		String deviceName = req.getBaseRequest().getDeviceName();
		deviceName = deviceName == null ? "" : deviceName;
		LoginResponse resp = new LoginResponse();
		BaseResponse baseResponse = new BaseResponse();
		Member member = new Member();
		String orgName="";
		if (times<timesLimit) {
		UserCriteria userCriteria = new UserCriteria();
		// userCriteria.createCriteria().andEmailEqualTo(username)
		// .andPasswordEqualTo(password);
/*		password = MD5Util.getMD5String(password);
*/		userCriteria.createCriteria().andPYQuanPinEqualTo(username).andPasswordEqualTo(password);
		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		
		
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			UserAO u = userListRet.getData().get(0);
			OrgUserCriteria orgUserCriteria = new OrgUserCriteria();
			orgUserCriteria.createCriteria().andUserIdEqualTo(u.getUid());
			ServiceResult<List<OrgUserAO>> orgUserListRet = orgUserService.selectByCriteria(orgUserCriteria);
			if (orgUserListRet.isSucceed()&&!CollectionUtils.isEmpty(orgUserListRet.getData())) {
				OrgUserAO orgUserAO = orgUserListRet.getData().get(0);
				OrgCriteria orgCriteria= new OrgCriteria();
				orgCriteria.createCriteria().andIdEqualTo(orgUserAO.getOrgId());
				ServiceResult<List<OrgAO>> orgListRet =orgService.selectByCriteria(orgCriteria);
				if (orgListRet.isSucceed() && !CollectionUtils.isEmpty(orgListRet.getData())) {
					OrgAO orgAO=orgListRet.getData().get(0);
					orgName=orgAO.getName();
				}
			}
			
			if (deviceId != null && !deviceId.equals("null") && u.getTenantId().equals(needbind_tenantId)) {
				int retint = CheckClientDeviceBind(deviceId, u.getUid(), deviceName);
				if (retint == 2|| retint == 0) {
					baseResponse.setErrMsg("该设备需要进行绑定才能登录使用");
					baseResponse.setRet(BaseResponse.RET_ERROR_UNBIND);
					resp.setBaseResponse(baseResponse);
					return resp;

				} else if (retint == 1 ) {
					
					member = userToMember(u);
					member.setOrgName(orgName);
					resp.setMember(member);
					String token = genToken(request.getSession().getId(), member);
					LOG.info("token is ", token);
					resp.setToken(token);
					resp.setUid(u.getUid());
					baseResponse.setRet(BaseResponse.RET_SUCCESS);
					baseResponse.setErrMsg("");
					resp.setBaseResponse(baseResponse);
					redisUtilService.set(username,deviceId);
					return resp;
				}
			} else {
				member = userToMember(u);
				member.setOrgName(orgName);
				resp.setMember(member);
				String token = genToken(request.getSession().getId(), member);
				LOG.info("token is ："+ token);
				resp.setToken(token);
				resp.setUid(u.getUid());
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				baseResponse.setErrMsg("");
				resp.setBaseResponse(baseResponse);
				redisUtilService.set(username,deviceId);
				LOG.info("deviceId is ："+ deviceId);
				String urlHost = loginAndOutUrl+u.getUid()+"/login";
				if ("true".equals(loginAndOutUrlOpen)) {
				LOG.info("urlHost is ："+ urlHost);
				String aa=	call(urlHost, "GET");
				LOG.info("调用登录登出接口返回值================ ："+ aa);
				}
				return resp;
			}
		}
		
		times++;
		
		redisUtilService.set(redisLoginTimesKey,times+"");
		redisUtilService.expire(WZTK_LOGIN_TIMES+username, timeLimitLeng);
		baseResponse.setErrMsg("登录失败 ："+ times +" 次 ,请确认用户名或者密码是否正确！");
		LOG.info("登陆失败 ：", "用户名或密码错误");
		baseResponse.setRet(BaseResponse.RET_ERROR_LOGIN);
		resp.setBaseResponse(baseResponse);
		return resp;
		}else {
			baseResponse.setErrMsg("登录失败次数过多，账号被锁定！");
			LOG.info("登陆失败 ：", "登录失败次数过多，账号被锁定！");
			baseResponse.setRet(BaseResponse.RET_ERROR_LOGIN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
	}

	/**
	 * 绑定状态
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBindDevices", method = { RequestMethod.POST })
	@ResponseBody
	public Object getBindDevices(@RequestBody BindRequest req, Model model, HttpServletRequest request) {

		String username = req.getUserName();
		String bindStatus = req.getBindStatus();
		String deviceName = req.getBaseRequest().getDeviceName();
		deviceName = deviceName == null ? "" : deviceName;

		ClientCustomizedCriteria clientCriteria = new ClientCustomizedCriteria();
		if (username != null && !username.isEmpty() && bindStatus != null && !bindStatus.isEmpty()) {
			clientCriteria.createCriteria().andNickNameLike("%" + username + "%").andBindstatusEqualTo(bindStatus);
		} else if (username != null && !username.isEmpty()) {
			clientCriteria.createCriteria().andNickNameLike("%" + username + "%");
		} else if (bindStatus != null && !bindStatus.isEmpty()) {
			clientCriteria.createCriteria().andBindstatusEqualTo(bindStatus);
		} else {
			clientCriteria.createCriteria();
		}

		List<ObjectClient> ret = clientService.getByCriteria(clientCriteria);
		return ret;
	}

	@RequestMapping(value = "/bindMultiDevices", method = { RequestMethod.POST })
	@ResponseBody
	public Object bindMultiDevices(@RequestBody MultiBindRequest req, Model model, HttpServletRequest request) {

		String bindStatus = req.getBindStatus();

		BindResponse resp = new BindResponse();
		BaseResponse baseResponse = new BaseResponse();
		ClientCriteria clientCriteria = new ClientCriteria();
		clientCriteria.createCriteria().andIdIn(req.idList);
		ServiceResult<List<ClientAO>> ret = clientService.selectByCriteria(clientCriteria);
		if (ret.isSucceed() && !CollectionUtils.isEmpty(ret.getData())) {
			for (ClientAO client : ret.getData()) {
				client.setBindstatus(bindStatus);
			}
		}
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		baseResponse.setErrMsg("");
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	/**
	 * 绑定状态
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindDevice", method = { RequestMethod.POST })
	@ResponseBody
	public Object bindDevice(@RequestBody BindRequest req, Model model, HttpServletRequest request) {
		String username = req.getUserName();
		String deviceId = req.getBaseRequest().getDeviceID();
		String deviceType = req.getBaseRequest().getDeviceType();
		String bindStatus = req.getBindStatus();
		String deviceName = req.getBaseRequest().getDeviceName();
		deviceName = deviceName == null ? "" : deviceName;

		BindResponse resp = new BindResponse();
		BaseResponse baseResponse = new BaseResponse();
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andPYQuanPinEqualTo(username);
		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			UserAO u = userListRet.getData().get(0);
			Boolean result = saveClientBindStatus(deviceId, deviceType, deviceName, u.getUid(), bindStatus);
			if (result) {
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				baseResponse.setErrMsg("");
				resp.setBaseResponse(baseResponse);
				return resp;
			}
		}

		baseResponse.setRet(BaseResponse.RET_ERROR);
		baseResponse.setErrMsg("");
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	/**
	 * 绑定状态
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDeviceBindStatus", method = { RequestMethod.POST })
	@ResponseBody
	public Object getDeviceBindStatus(@RequestBody BindRequest req, Model model, HttpServletRequest request) {
		String username = req.getUserName();
		String deviceId = req.getBaseRequest().getDeviceID();
		String deviceName = req.getBaseRequest().getDeviceName();
		deviceName = deviceName == null ? "" : deviceName;

		BindResponse resp = new BindResponse();
		BaseResponse baseResponse = new BaseResponse();
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andPYQuanPinEqualTo(username);
		ClientAO client = null;
		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			UserAO u = userListRet.getData().get(0);
			ClientCriteria clientCriteria = new ClientCriteria();
			clientCriteria.createCriteria().andUserIdEqualTo(u.getUid()).andDeviceIdEqualTo(deviceId);
			ServiceResult<List<ClientAO>> ret = clientService.selectByCriteria(clientCriteria);
			if (ret.isSucceed() && !CollectionUtils.isEmpty(ret.getData())) {
				client = ret.getData().get(0);
			}
		}

		Msg msg = new Msg();
		msg.msgType = 1001;
		msg.setObjectContent(client);
		resp.msg = msg;
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		baseResponse.setErrMsg("");
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	/**
	 * 保存綁定狀態
	 * 
	 * @param deviceId
	 * @param deviceType
	 * @param deviceName
	 * @param userId
	 * @param bindStatus
	 * @return
	 */
	private Boolean saveClientBindStatus(String deviceId, String deviceType, String deviceName, String userId,
			String bindStatus) {

		ClientCriteria clientCriteria = new ClientCriteria();
		clientCriteria.createCriteria().andUserIdEqualTo(userId).andDeviceIdEqualTo(deviceId);
		ServiceResult<List<ClientAO>> ret = clientService.selectByCriteria(clientCriteria);
		ClientAO client = null;
		if (ret.isSucceed() && !CollectionUtils.isEmpty(ret.getData())) {
			client = ret.getData().get(0);
		} else {
			client = new ClientAO();
		}
		client.setDeviceId(deviceId);
		client.setDeviceName(deviceName);
		client.setBindstatus(bindStatus);
		client.setUserId(userId);
		client.setType(deviceType);
		if (client.getId() == null || client.getId().isEmpty()) {
			client.setCreated(new Date());
			client.setUpdated(new Date());
			client.setLatestLoginTime(new Date());
		}
		ServiceResult<Boolean> result = clientService.saveOrUpdate(client);
		if (result.isSucceed()) {
			return result.getData();
		} else {
			return false;
		}
	}

	
	@RequestMapping(value = "/IsAppCanAccess/{deviceId}/{userId}/{deviceType}/{token}", method = { RequestMethod.GET })
	@ResponseBody
	public Object IsAppCanAccess(@PathVariable("deviceId") String deviceId, @PathVariable("userId") String userId, @PathVariable("deviceType") String deviceType,@PathVariable("token") String token, HttpServletRequest request) {
		ClientCriteria clientCriteria = new ClientCriteria();
		clientCriteria.createCriteria().andUserIdEqualTo(userId).andDeviceIdEqualTo(deviceId).andTypeEqualTo(deviceType).andBindstatusEqualTo("1");
		ServiceResult<List<ClientAO>> ret = clientService.selectByCriteria(clientCriteria);
		if (ret.isSucceed() && !CollectionUtils.isEmpty(ret.getData())) {
			return 1;
		}else{
			ServiceResult<Member> excittoken = getUserByToken(token);
			if (excittoken.isSucceed() || null != excittoken.getData()) {
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * 检查设备是否绑定到账户
	 * 
	 * @param deviceId
	 *            设备串码
	 * @param userId
	 *            用户Id
	 * @return 0 该账户未绑定任何设备 1该账户域登录用的设备id进行了绑定 2 该账户绑定了其他设备
	 */
	public int CheckClientDeviceBind(String deviceId, String userId, String deviceName) {
		ClientCriteria clientCriteria = new ClientCriteria();
		clientCriteria.createCriteria().andUserIdEqualTo(userId).andBindstatusEqualTo("1");
		ServiceResult<List<ClientAO>> ret = clientService.selectByCriteria(clientCriteria);
		if (ret.isSucceed() && !CollectionUtils.isEmpty(ret.getData())) {
			for (ClientAO client : ret.getData()) {
				if (client.getDeviceId().equals(deviceId)) {
					if (!client.getDeviceName().equals(deviceName)) {
						client.setDeviceName(deviceName);
						clientService.update(client);
					}
					return 1;
				}
			}
			return 2;
		}
		return 0;
	}

	private Member userToMember(UserAO u) {
		Member member = new Member();
		member.setUid(u.getUid());
		member.setTenantId(u.getTenantId());
		member.setUserName(u.getUid().concat(Member.SUFFIX_USER));
		member.setNickName(u.getNickName());
		member.setName(u.getName()); // openfire的name不支持正斜杠
		// member.setpYInitial(PinYinUtil.getFirstSpell(u.getNickName()));
		// member.setpYQuanPin(PinYinUtil.getFullSpell(u.getNickName()));
		member.setpYQuanPin(u.getpYQuanPin());
		member.setStatus(u.getStatus());
		member.setPassword(u.getPassword());
		member.setEmail(u.getEmail());
		member.setMobile(u.getMobile());
		member.setTel(u.getTel());

		if (StringUtils.isNotBlank(u.getAvatar())) {
			member.setAvatar(fileServePath.concat("/").concat(u.getAvatar()));
		}
		return member;
	}

	private Member appToMember(ApplicationAO u) {
		Member member = new Member();
		member.setUid(u.getId());
		member.setTenantId(u.getTenantId());
		member.setUserName(u.getId().concat(Member.SUFFIX_APP));
		member.setNickName(u.getName());
		member.setName(u.getName()); // openfire的name不支持正斜杠
		member.setpYInitial(u.getType());
		// member.setpYQuanPin(PinYinUtil.getFullSpell(u.getName()));
		member.setStatus(u.getStatus());
		member.setAvatar(u.getAvatar());
		member.setDescription(u.getDescription());
		member.setFollow(u.getFollow());

		if (StringUtils.isNotBlank(u.getAvatar())) {
			member.setAvatar(fileServePath.concat("/").concat(u.getAvatar()));
		}
		return member;
	}

	/**
	 * 生成Token
	 * 
	 * @param member
	 * @param sessionId
	 * @return
	 */
	private String genToken(String sessionId, Member member) {
		if (null == sessionId) {
			sessionId = UUID.randomUUID().toString();
		}
		// redis.key.expire 过期时间
		String token = sessionId;
		redisService.setDefaultExpire(token.getBytes(), SerializeUtil.serialize(member));

		return token;
	}

	/**
	 * 根据令牌返回用户. 如果该令牌可用，刷新其过期时间.
	 * 
	 * @param token
	 * @return
	 */
	private ServiceResult<Member> getUserByToken(String token) {
		ServiceResult<Member> ret = new ServiceResult<Member>();
		Boolean conn = redisService.exists(token);
		if (conn == null || conn == false) {
			return null;
		}
		Member m = (Member) redisService.get(token);
		ret.setData(m);
		ret.setSucceed(true);
		return ret;
	}

	/**
	 * 登出接口
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginOut", method = { RequestMethod.POST })
	@ResponseBody
	public Object loginOut(@RequestBody LoginRequest req, Model model, HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());
		LOG.info("进入退出登录接口    ：");
		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();
		String uid = req.getBaseRequest().getUid();
		baseResponse.setErrMsg("");
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		String urlHost = loginAndOutUrl+uid+"/logout";
		LOG.info("urlHost is ："+ urlHost);
		if ("true".equals(loginAndOutUrlOpen)) {
		String aa=	call(urlHost, "GET");
		LOG.info("调用登录登出接口返回值================ ："+ aa);
		}
		return resp;
	}

	/**
	 * 添加或删除联系人
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addOrRemoveContact", method = { RequestMethod.POST })
	@ResponseBody
	public Object addOrRemoveContact(@RequestBody AddOrRemoveContactRequest req, Model model,
			HttpServletRequest request) {
		LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(), request.getHeader("user-agent"));
		LOG.info("SessionId %s", request.getSession().getId());
		AddOrRemoveContactResponse resp = new AddOrRemoveContactResponse();
		BaseResponse baseResponse = new BaseResponse();
		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken == null) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		int starFriend = req.getStarFriend();
		String toUserId = req.getUid();
		String fromUserId = req.getBaseRequest().getUid();
		if (starFriend == 1) {
			if (isStar(fromUserId, toUserId)) {
				baseResponse.setErrMsg("已添加");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			}

			int sort = 1;
			UserUserCriteria uuc = new UserUserCriteria();
			uuc.createCriteria().andFromUserIdEqualTo(fromUserId);
			ServiceResult<List<UserUserAO>> uListRet = userUserService.selectByCriteria(uuc);
			if (uListRet.isSucceed() && !CollectionUtils.isEmpty(uListRet.getData())) {
				sort += uListRet.getData().size();
			}

			UserUserAO uua = new UserUserAO();

			uua.setFromUserId(fromUserId);
			uua.setToUserId(toUserId);
			uua.setRemarkName("");
			uua.setSort(sort);
			Date date = new Date();
			uua.setCreated(date);
			uua.setUpdated(date);
			ServiceResult<Boolean> ret = userUserService.saveOrUpdate(uua);
			if (ret.getData().booleanValue()) {
				baseResponse.setErrMsg("已添加");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
			} else {
				baseResponse.setErrMsg("更新出错");
				baseResponse.setRet(BaseResponse.RET_ERROR);
				resp.setBaseResponse(baseResponse);
			}
		} else {
			UserUserCriteria example = new UserUserCriteria();
			example.createCriteria().andFromUserIdEqualTo(fromUserId).andToUserIdEqualTo(toUserId);
			ServiceResult<Boolean> ret = userUserService.deleteByCriteria(example);
			if (ret.getData().booleanValue()) {
				baseResponse.setErrMsg("已删除");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
			} else {
				baseResponse.setErrMsg("删除失败");
				baseResponse.setRet(BaseResponse.RET_ERROR);
				resp.setBaseResponse(baseResponse);
			}
		}

		return resp;
	}

	/**
	 * 判断两个用户是否为常联系人
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	public boolean isStar(String fromUid, String toUId) {
		// UserUser us = new UserUser();
		// select 1 from user_user where from_user_id=? and to_user_id=?
		UserUserCriteria example = new UserUserCriteria();
		example.createCriteria().andFromUserIdEqualTo(fromUid).andToUserIdEqualTo(toUId);
		
		// example.createCriteria().andToUserIdEqualTo(toUId);
		ServiceResult<List<UserUserAO>> uus = userUserService.selectByCriteria(example);
		if (uus.getData().size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取Member信息
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMember", method = { RequestMethod.POST })
	@ResponseBody
	public Object getMember(@RequestBody GetMemberRequest req, Model model, HttpServletRequest request) {
		

		GetMemberResponse resp = new GetMemberResponse();
		BaseResponse baseResponse = new BaseResponse();
		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);
		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String userName = req.userName;
		String ruuid = userName.substring(0, userName.indexOf("@"));
		String ruu = userName.substring(userName.indexOf("@"), userName.length());

		String uid = req.getBaseRequest().getUid();

		if (ruu.contains(SUFFIX.USER_SUFFIX)) {
			ServiceResult<UserAO> r = userService.getById(ruuid);

			if (!r.isSucceed() || null == r.getData()) {
				baseResponse.setErrMsg("用户信息不存在");
				baseResponse.setRet(BaseResponse.RET_ERROR);
				resp.setBaseResponse(baseResponse);
				return resp;

			}
			// 查看是否常用联系人
			Member m = userToMember(r.getData());
			if (isStar(uid, ruuid)) {
				m.setStarFriend(1);
			} else {
				m.setStarFriend(0);
			}

			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			resp.member = m;
			return resp;

		} else if (ruu.contains(SUFFIX.APP_SUFFIX)) {
			ServiceResult<ApplicationAO> app = applicationService.getById(ruuid);
			if (!app.isSucceed() || null == app.getData()) {
				baseResponse.setErrMsg("应用信息不存在");
				baseResponse.setRet(BaseResponse.RET_ERROR);
				resp.setBaseResponse(baseResponse);
				return resp;

			}
			AppUserCriteria appUserCriteria = new AppUserCriteria();
			appUserCriteria.createCriteria().andUidEqualTo(excittoken.getData().getUid()).andAppidEqualTo(ruuid);
			ServiceResult<List<AppUserAO>> appUserListRet = appUserService.selectByCriteria(appUserCriteria);
			if (appUserListRet.isSucceed() && !CollectionUtils.isEmpty(appUserListRet.getData())) {
				app.getData().setFollow(appUserListRet.getData().get(0).getFollow());
			} else {
				app.getData().setFollow("0");
			}
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			resp.member = appToMember(app.getData());
			return resp;
		}
		baseResponse.setErrMsg("查询信息不存在");
		baseResponse.setRet(BaseResponse.RET_ERROR);
		resp.setBaseResponse(baseResponse);
		return resp;

	}

	@RequestMapping(value = "/getUserIdByName/{loginName}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getUserId(@PathVariable("loginName") String loginName, HttpServletRequest request) {

		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andPYQuanPinEqualTo(loginName);
		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			UserAO u = userListRet.getData().get(0);
			return u.getUid();
		}
		return "";
	}
	
	

	@RequestMapping(value = "/getAppOrgUserList/{tenantid}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getAppOrgUserList(@PathVariable("tenantid") String tenantid, HttpServletRequest request) {

		List<OrgUserAppResponse> resList = new ArrayList<OrgUserAppResponse>();

		ServiceResult<TenantAO> tenantRet = tenantService.getById(tenantid);
		if (tenantRet.isSucceed() && null != tenantRet.getData()) {
			TenantAO t = tenantRet.getData();
			OrgUserAppResponse OrgUserApp = new OrgUserAppResponse();
			OrgUserApp.setId(t.getId());
			OrgUserApp.setName(t.getName());
			OrgUserApp.setType("org");
			OrgUserApp.setpId("0");
			resList.add(OrgUserApp);
			OrgCriteria example = new OrgCriteria();
			example.createCriteria().andTenantIdEqualTo(tenantid);
			ServiceResult<List<OrgAO>> praentList = orgService.selectByCriteria(example);
			if (praentList.isSucceed() & !CollectionUtils.isEmpty(praentList.getData())) {
				// 所有组织
				for (OrgAO org : praentList.getData()) {
					OrgUserAppResponse Orgre = new OrgUserAppResponse();
					Orgre.setId(org.getId());
					Orgre.setName(org.getName());
					Orgre.setType("org");
					if (org.getParentId().equals("") || org.getParentId().isEmpty()) {
						Orgre.setpId(OrgUserApp.getId());
					} else {
						Orgre.setpId(org.getParentId());
					}
					resList.add(Orgre);
					List<UserAO> userList = GetUserList(Orgre.getId());
					for (UserAO user : userList) {
						OrgUserAppResponse userre = new OrgUserAppResponse();
						userre.setId(user.getUid());
						userre.setName(user.getNickName());
						userre.setType("user");
						userre.setpId(Orgre.getId());
						if(user.getAvatar()!=null){
						   userre.setIcon(fileServePath.concat("/").concat(user.getAvatar()));
						}
						resList.add(userre);
					}
				}

			}

		}
		return resList;
	}

	private List<UserAO> GetUserList(String orgId) {
		List<String> values = new ArrayList<String>();
		OrgUserCriteria example = new OrgUserCriteria();
		example.createCriteria().andOrgIdEqualTo(orgId);
		ServiceResult<List<OrgUserAO>> orgUserListRet = orgUserService.selectByCriteria(example);
		if (orgUserListRet.isSucceed() && !CollectionUtils.isEmpty(orgUserListRet.getData())) {
			for (OrgUserAO ogu : orgUserListRet.getData()) {
				values.add(ogu.getUserId());
			}

			UserCriteria userCriteria = new UserCriteria();
			userCriteria.createCriteria().andUidIn(values);
			ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
			if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
				return userListRet.getData();
			}
		}
		return new ArrayList<UserAO>();
	}

	/**
	 * 获取组织机构信息
	 * 
	 * @param req
	 * @param mode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOrgInfo", method = { RequestMethod.POST })
	@ResponseBody
	public Object getOrgInfo(@RequestBody GetOrgInfoRequest req, Model model, HttpServletRequest request) {
		
		GetOrgInfoResponse resp = new GetOrgInfoResponse();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		Member ognizationMemberList = new Member();

		resp.userOgnization = excittoken.getData().getTenantId();
		ServiceResult<TenantAO> tenantRet = tenantService.getById(excittoken.getData().getTenantId());
		if (tenantRet.isSucceed() && null != tenantRet.getData()) {
			TenantAO t = tenantRet.getData();
			ognizationMemberList.setUid(t.getId());
			ognizationMemberList.setNickName(t.getName());
			ognizationMemberList.setName(t.getName());
			ognizationMemberList.setUserName(t.getId().concat(Member.SUFFIX_TENANT));
			List<Member> menberlist = new ArrayList<Member>();
			OrgCriteria example = new OrgCriteria();
			example.createCriteria().andTenantIdEqualTo(excittoken.getData().getTenantId());
			ServiceResult<List<OrgAO>> praentList = orgService.selectByCriteria(example);
			if (praentList.isSucceed() & !CollectionUtils.isEmpty(praentList.getData())) {
				// 所有组织
				OrgCriteria example2 = new OrgCriteria();
				example2.createCriteria().andIdIsNotNull();
				ServiceResult<List<OrgAO>> allList = orgService.selectByCriteria(example2);
				for (OrgAO org : praentList.getData()) {
					menberlist.add(getOrg(org, allList.getData()));
				}

				OrgUserCriteria orgUserCriteria = new OrgUserCriteria();
				orgUserCriteria.createCriteria().andUserIdEqualTo(excittoken.getData().getUid());
				ServiceResult<List<OrgUserAO>> ret = orgUserService.selectByCriteria(orgUserCriteria);
				if (ret.isSucceed() && !CollectionUtils.isEmpty(ret.getData())) {

					ServiceResult<OrgAO> orgRet = orgService.getById(ret.getData().get(0).getOrgId());
					if (orgRet.isSucceed() && null != orgRet.getData()) {
						resp.userOgnization = orgRet.getData().getId();
					}

				}
				ognizationMemberList.setMemberList(menberlist);
				ognizationMemberList.setMemberCount(menberlist.size());
			}

		}

		resp.ognizationMemberList = ognizationMemberList;
		List<Member> starMemberList = new ArrayList<Member>();
		starMemberList = getStarUser(excittoken.getData().getUid());
		resp.starMemberList = starMemberList;
		if (starMemberList != null) {
			resp.starMemberCount = starMemberList.size();
		}
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	public Member getOrg(OrgAO orgUnit, List<OrgAO> list) {
		Member tmp = new Member();
		tmp.setUid(orgUnit.getId());
		tmp.setUserName(orgUnit.getId().concat(Member.SUFFIX_ORG));
		tmp.setNickName(orgUnit.getName());
		tmp.setStarFriend(0);
		tmp.setSort(orgUnit.getSort());
		List<Member> listmember = new ArrayList<Member>();
		for (int i = 0; i < list.size(); i++) {
			if (orgUnit.getId().equals(list.get(i).getParentId())) {
				Member tmpchild = getOrg(list.get(i), list);
				listmember.add(tmpchild);
			}
		}
		tmp.setMemberCount(listmember.size());
		tmp.setMemberList(listmember);
		return tmp;
	}

	/**
	 * 迭代树节点
	 * 
	 * @param tcd
	 * @return
	 */
	public Member recursionTenant(Map tcd, String id) {

		Member rec = new Member();
		List<Member> data = new ArrayList<Member>();
		if ((String) tcd.get("id") == null) {
			rec.uid = id;
			rec.userName = id + SUFFIX.ORG_SUFFIX;
		} else {
			rec.uid = (String) tcd.get("id");
			rec.userName = (String) tcd.get("id") + SUFFIX.ORG_SUFFIX;
		}
		rec.name = (String) tcd.get("name");
		rec.nickName = (String) tcd.get("name");
		List<Member> chirldrendata = new ArrayList<Member>();
		if (tcd.get("chirldren") != "null") {

			ArrayList d = (ArrayList) tcd.get("chirldren");
			if (d != null) {
				for (int i = 0; i < d.size(); i++) {
					Member val = recursionTenant((Map) d.get(i), id);
					chirldrendata.add(val);
				}
			}
		}
		rec.memberList = chirldrendata;
		rec.memberCount = chirldrendata.size();
		return rec;
	}

	/**
	 * 获取星标好友
	 * 
	 * @param tcd
	 * @return
	 */
	public List<Member> getStarUser(String uid) {
		List<Member> ret = new ArrayList<Member>();
		List<UserAO> star = userUserService.getstar(uid);
		for (int i = 0; i < star.size(); i++) {
			Member tmp = new Member();
			tmp.setUid(star.get(i).getUid());
			tmp.setUserName(star.get(i).getUid() + SUFFIX.USER_SUFFIX);
			tmp.setName(star.get(i).getName());
			tmp.setNickName(star.get(i).getNickName());
			tmp.setStatus(star.get(i).getStatus());
			tmp.setAvatar(star.get(i).getAvatar());
			tmp.setTenantId(star.get(i).getTenantId());
			tmp.setEmail(star.get(i).getEmail());
			tmp.setpYInitial(star.get(i).getpYInitial());
			tmp.setpYQuanPin(star.get(i).getpYQuanPin());
			tmp.setMobile(star.get(i).getMobile());
			tmp.setTel(star.get(i).getTel());
			ret.add(tmp);
		}
		return ret;
	}

	/**
	 * 获取组织下面的用户
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOrgUserList", method = { RequestMethod.POST })
	@ResponseBody
	public Object getOrgUserList(@RequestBody GetOrgUserListRequest req, Model model, HttpServletRequest request) {
		

		GetOrgUserListResponse resp = new GetOrgUserListResponse();
		BaseResponse baseResponse = new BaseResponse();
		List<Member> memberList = new ArrayList<Member>();

		String token = req.getBaseRequest().getToken();
		String orgId = req.getOrgid();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		List<String> values = new ArrayList<String>();
		OrgUserCriteria example = new OrgUserCriteria();
		example.createCriteria().andOrgIdEqualTo(orgId);
		ServiceResult<List<OrgUserAO>> orgUserListRet = orgUserService.selectByCriteria(example);
		if (orgUserListRet.isSucceed() && !CollectionUtils.isEmpty(orgUserListRet.getData())) {
			for (OrgUserAO ogu : orgUserListRet.getData()) {
				values.add(ogu.getUserId());
			}

			UserCriteria userCriteria = new UserCriteria();
			userCriteria.createCriteria().andUidIn(values);
			ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
			if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
				for (UserAO u : userListRet.getData()) {
					u.setPassword("");//不让密码显示出来。
					memberList.add(userToMember(u));
				}
			}
		}
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		resp.memberList = memberList;
		resp.memberCount = memberList.size();
		return resp;
	}

	/**
	 * 检查更新
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkUpdate", method = { RequestMethod.POST })
	@ResponseBody
	public Object checkUpdate(@RequestBody CheckUpdateRequest req, Model model, HttpServletRequest request) {
		

		CheckUpdateResponse resp = new CheckUpdateResponse();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		int versionCode = req.getVersionCode();
		ClientVersionCriteria example = new ClientVersionCriteria();
		example.createCriteria().andTypeEqualTo(req.getType());
		ServiceResult<List<ClientVersionAO>> uRet = clientVersionService.selectByCriteria(example);
		ClientVersionAO retao = null;
		if (uRet.isSucceed() && !CollectionUtils.isEmpty(uRet.getData())) {
			retao = uRet.getData().get(0);
		} else {
			baseResponse.setErrMsg("查找不到的应用");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String content = retao.getVerDescription();

		Msg msg = new Msg();
		msg.msgType = 1001;
		msg.setContent(content);
		msg.setObjectContent(retao);
		resp.msg = msg;
		if (versionCode < retao.getVerCode()) {

			resp.isUpdate = true;

		} else {
			resp.isUpdate = false;

		}
		baseResponse.setErrMsg("");
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	/**
	 * 检查插件更新
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkPlugUpdate", method = { RequestMethod.POST })
	@ResponseBody
	public Object checkPlugUpdate(@RequestBody CheckPlugUpdateRequest req, Model model, HttpServletRequest request) {

		CheckPlugUpdateResponse resp = new CheckPlugUpdateResponse();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

		AppUserAllowCriteria allowCriteria = new AppUserAllowCriteria();
		// 获取用户允许关注应用列表
		allowCriteria.createCriteria().andUidEqualTo(excittoken.getData().getUid()).andAppidEqualTo(req.getAppid());
		ServiceResult<List<AppUserAllowAO>> appuserallowListRet = appUserAllowService.selectByCriteria(allowCriteria);
		AppUserAllowAO appuserallow = null;
		if (appuserallowListRet.isSucceed() && !CollectionUtils.isEmpty(appuserallowListRet.getData())) {
			appuserallow = appuserallowListRet.getData().get(0);
		} else {
			baseResponse.setErrMsg("没有关注的应用");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

		int versionCode = req.getVersionCode();
		ClientPluginVersionCriteria example = new ClientPluginVersionCriteria();
		example.createCriteria().andTypeEqualTo(req.getType()).andAppidEqualTo(req.getAppid());
		ServiceResult<List<ClientPluginVersionAO>> uRet = clientPluginVersionService.selectByCriteria(example);
		ClientPluginVersionAO retao = null;
		if (uRet.isSucceed() && !CollectionUtils.isEmpty(uRet.getData())) {
			if(uRet.getData().size()==1){
			   retao = uRet.getData().get(0);
			}else{
				for (ClientPluginVersionAO tao : uRet.getData()) {
					if(tao.getVerName().equals("ver_1.0")){
						retao=tao;
					}
					if(tao.getVerName().toLowerCase().equals(req.baseRequest.getDeviceName().toLowerCase())){
						retao=tao;
						break;
					}
				}
			}
		} else {
			baseResponse.setErrMsg("没有关注的应用");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String content ="";
		if (null!=retao) {
			 content = retao.getVerDescription();
			if (appuserallow.getType().equals("1")) {
				retao.setDownloadUrl(rejectInternatUrl);
			} else if (appuserallow.getType().equals("2")) {
				retao.setLanUrl(rejectLanUrl);
			}
		
		Msg msg = new Msg();
		msg.msgType = 1001;
		msg.setContent(content);
		msg.setObjectContent(retao);
		resp.msg = msg;
		if (versionCode < retao.getVerCode()) {

			resp.isUpdate = true;

		} else {
			resp.isUpdate = false;

		}
		
		}
		baseResponse.setErrMsg("");
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		
		
		return resp;
	}

	/**
	 * 查找用户
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	@ResponseBody
	public Object search(@RequestBody SearchRequest req, Model model, HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());

		SearchResponse resp = new SearchResponse();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String searchKey = req.searchKey;
		String searchType = req.searchType;
		int pageCount = req.pageCount;
		int pageNo = req.pageNo;
		String tenantId = excittoken.getData().getTenantId();
		List<Member> members = new ArrayList<Member>();
		switch (searchType) {
		case "user":
			members = searchUser(searchKey, tenantId, pageCount, pageNo);
			break;
		case "uid":
			members = searchUserByid(searchKey, tenantId, pageCount, pageNo);	
			break;
		case "app":
			break;
		}

		resp.memberListSize = members.size();
		resp.count = members.size();
		resp.memberList = members;
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;

	}

	/**
	 * 查找用户
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeUserInfo", method = { RequestMethod.POST })
	@ResponseBody
	public Object changeUserInfo(@RequestBody ChangeUserInfoRequest req, Model model, HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());

		ChangeUserInfoResponse resp = new ChangeUserInfoResponse();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andUidEqualTo(req.getUid());
		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			UserAO u = userListRet.getData().get(0);
			u.setEmail(req.getEmail());
			u.setMobile(req.getMobile());
			u.setTel(req.getTel());
			String password = MD5Util.getMD5String(req.getPassword());
			u.setPassword(password);
			ServiceResult<Boolean> save = userService.update(u);
			if (save.getData().booleanValue()) {
				baseResponse.setErrMsg("修改信息成功");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			}

		}
		baseResponse.setErrMsg("修改信息不成功");
		baseResponse.setRet(BaseResponse.RET_ERROR);
		resp.setBaseResponse(baseResponse);
		return resp;

	}

	/**
	 * 搜索用户
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 */

	public List<Member> searchUser(String searchKey, String tenantId, int pageCount, int pageNo) {
		List<Member> memberList = new ArrayList<Member>();
		Page page = new Page();
		page.setPageCount(pageCount);
		page.setPageNo(pageNo);

		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andNickNameLike("%" + searchKey + "%").andTenantIdEqualTo(tenantId);
		userCriteria.setPage(page);

		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			for (UserAO u : userListRet.getData()) {
				memberList.add(userToMember(u));
			}
		}
		return memberList;
	}

	public List<Member> searchUserByid(String uid, String tenantId, int pageCount, int pageNo) {
		List<Member> memberList = new ArrayList<Member>();
		Page page = new Page();
		page.setPageCount(pageCount);
		page.setPageNo(pageNo);

		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andUidEqualTo(uid).andTenantIdEqualTo(tenantId);
		userCriteria.setPage(page);

		ServiceResult<List<UserAO>> userListRet = userService.selectByCriteria(userCriteria);
		if (userListRet.isSucceed() && !CollectionUtils.isEmpty(userListRet.getData())) {
			for (UserAO u : userListRet.getData()) {
				memberList.add(userToMember(u));
			}
		}
		return memberList;
	}
	
	@RequestMapping(value = "/addApnsToken", method = { RequestMethod.POST })
	@ResponseBody
	public Object addApnsToken(@RequestBody AddApnsTokenRequest req, Model model, HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());

		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();

		String deviceId = req.getBaseRequest().getDeviceID();
		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String apnsTokenStr = req.apnsToken;
		ApnsTokenAO apnsToken = new ApnsTokenAO();

		if (apnsTokenStr == "ok") {
			apnsToken.setUserId(excittoken.getData().getUid());
			apnsToken.setDeviceId(deviceId);
			apnsToken.setApnsToken(apnsTokenStr);
			Date date = new Date();

			apnsToken.setUpdated(date);
			apnsToken.setCreated(date);
		}
		apnsTokenService.deleteById(apnsToken.getDeviceId());
		ServiceResult<Boolean> save = apnsTokenService.saveOrUpdate(apnsToken);
		if (save.getData().booleanValue()) {
			baseResponse.setErrMsg("Save apns_token success");
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		return resp;
	}

	@RequestMapping(value = "/delApnsToken", method = { RequestMethod.POST })
	@ResponseBody
	public Object delApnsToken(@RequestBody DelApnsTokenRequest req, Model model, HttpServletRequest request) {
		
		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String apnsTokenStr = req.getApnsToken();
		if (apnsTokenStr == "ok") {
			// 删除方法
			apnsTokenService.deleteById(req.getBaseRequest().getDeviceID());
			baseResponse.setErrMsg("Delete apns_token success");
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);

		} else {
			baseResponse.setErrMsg("Delete apns_token faild");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);

		}
		return resp;
	}

	@RequestMapping(value = "/setSessionState", method = { RequestMethod.POST })
	@ResponseBody
	public Object setSessionState(Model model, HttpServletRequest request) {
		return null;
	}

	@RequestMapping(value = "/getAppUserAllow/{userid}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getAppUserAllow(@PathVariable("userid") String userid, HttpServletRequest request,
			HttpServletResponse response) {

		AppUserAllowCriteria allowCriteria = new AppUserAllowCriteria();
		// 获取用户允许关注应用列表
		allowCriteria.createCriteria().andUidEqualTo(userid);
		ServiceResult<List<AppUserAllowAO>> appuserallowListRet = appUserAllowService.selectByCriteria(allowCriteria);
		List<AppUserAllowAO> appuserallow = new ArrayList<AppUserAllowAO>();
		if (appuserallowListRet.isSucceed() && !CollectionUtils.isEmpty(appuserallowListRet.getData())) {
			for (AppUserAllowAO allow : appuserallowListRet.getData()) {
				appuserallow.add(allow);
			}
		}
		return appuserallow;
	}

	/**
	 * 清楚用户授权信息
	 * 
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteAppUserAllow", method = { RequestMethod.POST })
	@ResponseBody
	public Object deleteAppUserAllow(@RequestBody SaveAppUserAllowRequest req, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取用户允许关注应用列表
		List<String> userlist = new ArrayList<String>();
		for (AppUserAllowAO allow : req.AppUserAllowAOList) {
			if (!userlist.contains(allow.getUid())) {
				userlist.add(allow.getUid());
			}
		}

		AppUserAllowCriteria deleteallowCriteria = new AppUserAllowCriteria();
		deleteallowCriteria.createCriteria().andUidIn(userlist);
		ServiceResult<Boolean> retde = appUserAllowService.deleteByCriteria(deleteallowCriteria);
		return retde.getData();
	}

	/**
	 * 保存授权信息
	 * 
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/SaveAppUserAllow", method = { RequestMethod.POST })
	@ResponseBody
	public Object SaveAppUserAllow(@RequestBody SaveAppUserAllowRequest req, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取用户允许关注应用列表
		List<String> userlist = new ArrayList<String>();
		for (AppUserAllowAO allow : req.AppUserAllowAOList) {
			if (!userlist.contains(allow.getUid())) {
				userlist.add(allow.getUid());
			}
		}

		AppUserAllowCriteria deleteallowCriteria = new AppUserAllowCriteria();
		deleteallowCriteria.createCriteria().andUidIn(userlist);
		for (AppUserAllowAO allow : req.AppUserAllowAOList) {
			AppUserAllowCriteria allowCriteria = new AppUserAllowCriteria();
			allowCriteria.createCriteria().andAppidEqualTo(allow.getAppid()).andUidEqualTo(allow.getUid());
			ServiceResult<List<AppUserAllowAO>> appuserallowListRet = appUserAllowService
					.selectByCriteria(allowCriteria);
			if (appuserallowListRet.isSucceed() && !CollectionUtils.isEmpty(appuserallowListRet.getData())) {
				allow.setId(appuserallowListRet.getData().get(0).getId());
				ServiceResult<Boolean> ret = appUserAllowService.saveOrUpdate(allow);
				ret.getMsg();
			}
			
		}
		
		return 0;
	}

	@RequestMapping(value = "/getAllApplication", method = { RequestMethod.GET })
	@ResponseBody
	public Object getAllApplication(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("testdasdfasdfa");
		GetApplicationListResponse resp = new GetApplicationListResponse();
		BaseResponse baseResponse = new BaseResponse();
		List<Member> members = new ArrayList<Member>();

		ApplicationCriteria applicationCriteria = new ApplicationCriteria();
		applicationCriteria.createCriteria().andStatusEqualTo(0);
		ServiceResult<List<ApplicationAO>> appListRet = applicationService.selectByCriteria(applicationCriteria);
		if (appListRet.isSucceed() && !CollectionUtils.isEmpty(appListRet.getData())) {
			for (ApplicationAO app : appListRet.getData()) {
				app.setFollow("0");
				members.add(appToMember(app));
			}
		}
		resp.memberList = members;
		resp.memberCount = members.size();
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	@RequestMapping(value = "/getAllApplicationList", method = { RequestMethod.POST })
	@ResponseBody
	public Object getAllApplicationList(@RequestBody GetApplicationListRequest req, HttpServletRequest request,
			HttpServletResponse response) {

		GetApplicationListResponse resp = new GetApplicationListResponse();
		BaseResponse baseResponse = new BaseResponse();
		List<Member> members = new ArrayList<Member>();
		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

		AppUserAllowCriteria allowCriteria = new AppUserAllowCriteria();
		// 获取用户允许关注应用列表
		allowCriteria.createCriteria().andUidEqualTo(excittoken.getData().getUid());
		ServiceResult<List<AppUserAllowAO>> appuserallowListRet = appUserAllowService.selectByCriteria(allowCriteria);
		List<String> appidlist = new ArrayList<String>();
		if (appuserallowListRet.isSucceed() && !CollectionUtils.isEmpty(appuserallowListRet.getData())) {
			for (AppUserAllowAO app : appuserallowListRet.getData()) {
				appidlist.add(app.getAppid());
			}
		} else {
			resp.memberList = members;
			resp.memberCount = members.size();
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		ApplicationCriteria applicationCriteria = new ApplicationCriteria();
		applicationCriteria.createCriteria().andStatusEqualTo(0);
		ServiceResult<List<ApplicationAO>> appListRet = applicationService.selectByCriteria(applicationCriteria);
		if (appListRet.isSucceed() && !CollectionUtils.isEmpty(appListRet.getData())) {
			for (ApplicationAO app : appListRet.getData()) {
				app.setFollow("0");
				if (appidlist.contains(app.getId()))
					members.add(appToMember(app));
			}
		}

		AppUserCriteria appUserCriteria = new AppUserCriteria();
		// 用户关注的应用
		appUserCriteria.createCriteria().andUidEqualTo(excittoken.getData().getUid()).andFollowEqualTo("1");
		ServiceResult<List<AppUserAO>> appUserListRet = appUserService.selectByCriteria(appUserCriteria);
		if (appUserListRet.isSucceed() && !CollectionUtils.isEmpty(appUserListRet.getData())) {
			for (int i = 0; i < members.size(); i++) {
				if (members.get(i).getFollow() == "1") {
					continue;
				}
				for (AppUserAO au : appUserListRet.getData()) {
					if (members.get(i).getUid().equals(au.getAppid())) {
						members.get(i).setFollow("1");
						break;
					}
				}
			}
		}

		resp.memberList = members;
		resp.memberCount = members.size();
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	@RequestMapping(value = "/getApplicationList", method = { RequestMethod.POST })
	@ResponseBody
	public Object getApplicationList(@RequestBody GetApplicationListRequest req, Model model,
			HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());
		GetApplicationListResponse resp = new GetApplicationListResponse();
		BaseResponse baseResponse = new BaseResponse();
		List<Member> members = new ArrayList<Member>();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if ( excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed() ) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

		AppUserAllowCriteria allowCriteria = new AppUserAllowCriteria();
		// 获取用户允许关注应用列表
		allowCriteria.createCriteria().andUidEqualTo(excittoken.getData().getUid());
		ServiceResult<List<AppUserAllowAO>> appuserallowListRet = appUserAllowService.selectByCriteria(allowCriteria);
		List<String> appidlist = new ArrayList<String>();
		if (appuserallowListRet.isSucceed() && !CollectionUtils.isEmpty(appuserallowListRet.getData())) {
			for (AppUserAllowAO app : appuserallowListRet.getData()) {
				appidlist.add(app.getAppid());
			}
		} else {
			resp.memberList = members;
			resp.memberCount = members.size();
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

		List<String> values = new ArrayList<String>();
		AppUserCriteria appUserCriteria = new AppUserCriteria();
		// 用户关注的应用
		appUserCriteria.createCriteria().andUidEqualTo(excittoken.getData().getUid()).andFollowEqualTo("1");
		ServiceResult<List<AppUserAO>> appUserListRet = appUserService.selectByCriteria(appUserCriteria);
		if (appUserListRet.isSucceed() && !CollectionUtils.isEmpty(appUserListRet.getData())) {
			for (AppUserAO au : appUserListRet.getData()) {
				if (appidlist.contains(au.getAppid()))
					values.add(au.getAppid());
			}
		}
		if (values.size() > 0) {
			ApplicationCriteria applicationCriteria = new ApplicationCriteria();
			applicationCriteria.createCriteria().andIdIn(values).andStatusEqualTo(0);
			ServiceResult<List<ApplicationAO>> appListRet = applicationService.selectByCriteria(applicationCriteria);
			if (appListRet.isSucceed() && !CollectionUtils.isEmpty(appListRet.getData())) {
				for (ApplicationAO app : appListRet.getData()) {
					members.add(appToMember(app));
				}
			}
		}

		resp.memberList = members;
		resp.memberCount = members.size();
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}

	@RequestMapping(value = "/getAppOperationList", method = { RequestMethod.POST })
	@ResponseBody
	public Object getAppOperationList(@RequestBody GetAppOperationListRequest req, Model model,
			HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());

		GetAppOperationListResponse resp = new GetAppOperationListResponse();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String username = req.username;
		if (!username.contains(SUFFIX.APP_SUFFIX)) {
			baseResponse.setErrMsg("username 不包含应用信息");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String appid = username.substring(0, username.indexOf("@"));
		List<Operation> ptlist = new ArrayList<Operation>();

		OperationCriteria example = new OperationCriteria();
		example.createCriteria().andAppIdEqualTo(appid);
		ServiceResult<List<OperationAO>> opertionsListRet = operationService.selectByCriteria(example);
		if (opertionsListRet.isSucceed() && !CollectionUtils.isEmpty(opertionsListRet.getData())) {
			OperationCriteria example2 = new OperationCriteria();
			example2.createCriteria().andIdIsNotNull();
			ServiceResult<List<OperationAO>> allOpListRet = operationService.selectByCriteria(example2);
			if (allOpListRet.isSucceed() && !CollectionUtils.isEmpty(allOpListRet.getData())) {
				for (OperationAO opt : opertionsListRet.getData()) {
					ptlist.add(getOperation(opt, allOpListRet.getData()));
				}
			}
		}
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		resp.operationList = ptlist;
		resp.operationCount = ptlist.size();
		return resp;
	}

	// 构建选项树
	public Operation getOperation(OperationAO opt, List<OperationAO> list) {
		Operation o = new Operation();
		o.setAppId(opt.getAppId());
		o.setAction(opt.getAction());
		o.setContent(opt.getContent());
		o.setId(opt.getId());
		o.setSort(opt.getSort());
		o.setOperationType(opt.getOperationType());

		List<Operation> listmember = new ArrayList<Operation>();
		for (int i = 0; i < list.size(); i++) {
			if (opt.getId().equals(list.get(i).getParentId())) {
				Operation tmpchild = getOperation(list.get(i), list);
				listmember.add(tmpchild);
			}
		}
		o.setOperationList(listmember);
		return o;
	}

	@RequestMapping(value = "/followApp", method = { RequestMethod.POST })
	@ResponseBody
	public Object followApp(@RequestBody FollowAppRequest req, Model model, HttpServletRequest request) {
		// LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		// request.getHeader("user-agent"));
		// LOG.info("SessionId %s", request.getSession().getId());

		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();

		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String appName = req.appname;
		if (!appName.contains(SUFFIX.APP_SUFFIX)) {
			baseResponse.setErrMsg("appname is error format");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String appid = appName.substring(0, appName.indexOf("@"));
		String uid = req.getBaseRequest().getUid();

		AppUserCriteria ac = new AppUserCriteria();
		ac.createCriteria().andAppidEqualTo(appid).andUidEqualTo(uid);
		ServiceResult<List<AppUserAO>> auListRet = appUserService.selectByCriteria(ac);
		if (auListRet.isSucceed() && !CollectionUtils.isEmpty(auListRet.getData())) {
			AppUserAO au = auListRet.getData().get(0);
			au.setFollow("1");
			ServiceResult<Boolean> saveaua = appUserService.saveOrUpdate(au);
			if (saveaua.getData().booleanValue()) {

				baseResponse.setErrMsg("followApp success");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			}
		} else {

			AppUserAO aua = new AppUserAO();
			aua.setUid(req.getBaseRequest().getUid());
			aua.setFollow("1");
			aua.setAppid(appid);
			ServiceResult<Boolean> saveaua = appUserService.saveOrUpdate(aua);
			if (saveaua.getData().booleanValue()) {

				baseResponse.setErrMsg("followApp success");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			}
		}

		baseResponse.setErrMsg("followApp failed");
		baseResponse.setRet(BaseResponse.RET_ERROR);
		resp.setBaseResponse(baseResponse);
		return resp;

		
	}

	@RequestMapping(value = "/unFollowApp", method = { RequestMethod.POST })
	@ResponseBody
	public Object unFollowApp(@RequestBody UnFollowAppRequest req, Model model, HttpServletRequest request) {
		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();
		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		String appname = req.appname;
		if (!appname.contains(SUFFIX.APP_SUFFIX)) {
			baseResponse.setErrMsg("appname is error format");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
		}
		String appid = appname.substring(0, appname.indexOf("@"));
		String uid = req.getBaseRequest().getUid();

		AppUserCriteria ac = new AppUserCriteria();
		ac.createCriteria().andAppidEqualTo(appid).andUidEqualTo(uid);
		ServiceResult<List<AppUserAO>> auListRet = appUserService.selectByCriteria(ac);
		if (auListRet.isSucceed() && !CollectionUtils.isEmpty(auListRet.getData())) {
			AppUserAO au = auListRet.getData().get(0);
			au.setFollow("0");
			ServiceResult<Boolean> saveaua = appUserService.saveOrUpdate(au);
			if (saveaua.getData().booleanValue()) {

				baseResponse.setErrMsg("unFollowApp success");
				baseResponse.setRet(BaseResponse.RET_SUCCESS);
				resp.setBaseResponse(baseResponse);
				return resp;
			} else {
				return null;
			}
		} else {
			baseResponse.setErrMsg("unFollowApp success");
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

	}
	
	@RequestMapping(value = "/getUserAvatar", method = { RequestMethod.GET })
	public void getUserAvatar(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 LOG.info(" 进入获取头像接口  ");
		String userName = request.getParameter("userName");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		if (userName.contains(SUFFIX.USER_SUFFIX)) {
			ServiceResult<UserAO> u = userService.getById(userName.substring(0, userName.indexOf("@")));
			try {
				if (!u.getData().getAvatar().isEmpty()) {
					LOG.info(" 头像名称为空  ",u.getData().getAvatar());
					String addr = getdownloadurl(u.getData().getAvatar()) + "/" + u.getData().getAvatar() + "?width=" + width + "&height="
							+ height;
					
					byte[] respond = HttpUtil.doGet(addr);
					if(respond==null){
		    			return;
		    		}
					// 3.通过response获取ServletOutputStream对象(out)
					OutputStream output = response.getOutputStream();
					IOUtils.copy(new ByteArrayInputStream(respond), output);
					IOUtils.closeQuietly(output);
				}else {
					 LOG.info(" 获取头像名称为空  ");
					 
				}
				
			} catch (Exception e) {
			}
		}

		if (userName.contains(SUFFIX.APP_SUFFIX)) {
			// app(userName.substring(0,userName.indexOf("@")));
			try {
				ServiceResult<ApplicationAO> app = applicationService
						.getById(userName.substring(0, userName.indexOf("@")));
				String addr = getdownloadurl(app.getData().getAvatar()) + "/" + app.getData().getAvatar() + "?width=" + width + "&height="
						+ height;
				// String addr = "http://192.168.1.22:5084/"+
				// app.getData().getAvatar() + "?width=" + width+ "&height=" +
				// height;
				byte[] respond = HttpUtil.doGet(addr);
				if(respond==null){
	    			return;
	    		}
				OutputStream output = response.getOutputStream();
				IOUtils.copy(new ByteArrayInputStream(respond), output);
				IOUtils.closeQuietly(output);
			} catch (Exception e) {
			}
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

	@RequestMapping(value = "/setUserAvatar", method = { RequestMethod.POST })
	@ResponseBody
	public Object setUserAvatar(@RequestBody SetUserAvatarRequest req, Model model, HttpServletRequest request) {
		
		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();
		String token = req.getBaseRequest().getToken();
		ServiceResult<Member> excittoken = getUserByToken(token);

		if (excittoken==null||null == excittoken.getData()|| !excittoken.isSucceed()) {
			baseResponse.setErrMsg("会话超时请重新登陆");
			baseResponse.setRet(BaseResponse.RET_ERROR_TOKEN);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
		ResponseUpload responseUpload = req.responseUpload;
		String userName = req.userName;
		String fileId = responseUpload.getFid();
		if (!saveAvatar(userName, fileId)) {
			baseResponse.setErrMsg("数据写入失败");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}

		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}
	
	
	@RequestMapping(value = "/SaveClientLog", method = { RequestMethod.POST })
	@ResponseBody
	public Object SaveClientLog(@RequestBody ClientLogRequest req, Model model, HttpServletRequest request) {
		LOG.info("调用日志保存接口，保存错误信息到后台");
		LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
		 request.getHeader("user-agent"));
		 LOG.info("SessionId %s", request.getSession().getId());
		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();
		ClientLogAO clientLogAo=new ClientLogAO();
		clientLogAo.setDeviceName(req.getDeviceName());
		clientLogAo.setOsVersion(req.getOsVersion());
		clientLogAo.setUid(req.getUid());
		clientLogAo.setUname(req.getUname());
		clientLogAo.setFid(req.getFid());
		clientLogAo.setAppVersion(req.getAppVersion());
		clientLogAo.setCreated(new Date());
		ServiceResult<Boolean> result=clientLogService.saveOrUpdate(clientLogAo);
	
		if (result.isSucceed()) {
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			return resp;
		} else {
			baseResponse.setErrMsg("数据写入失败");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
	}

	@RequestMapping(value = "/SaveClientData", method = { RequestMethod.POST })
	@ResponseBody
	public Object SaveClientData(@RequestBody MobilDataRequest req, Model model, HttpServletRequest request) {
		LOG.info("调用用户分析系统，上传数据到数据库");
		 LOG.info("SessionId %s", request.getSession().getId());
		Response resp = new Response();
		BaseResponse baseResponse = new BaseResponse();
		String clientData=req.getClientData();
		LOG.info("clientData====", clientData);
		ServiceResult<Boolean> result=null;
		 List<ClientLogAO> clientList = com.alibaba.fastjson.JSONObject.parseArray(clientData, ClientLogAO.class);
		 for (Iterator iterator = clientList.iterator(); iterator.hasNext();) {
			ClientLogAO clientLogAO = (ClientLogAO) iterator.next();
			clientLogAO.setCreated(new Date());
			result=clientLogService.saveOrUpdate(clientLogAO);
		}
		 		
		if (result.isSucceed()) {
			baseResponse.setRet(BaseResponse.RET_SUCCESS);
			resp.setBaseResponse(baseResponse);
			return resp;
		} else {
			baseResponse.setErrMsg("数据写入失败");
			baseResponse.setRet(BaseResponse.RET_ERROR);
			resp.setBaseResponse(baseResponse);
			return resp;
		}
	}
	
	
	/**
	 * 修改头像
	 * 
	 * @param userName
	 * @param avatar
	 * @return
	 */
	public boolean saveAvatar(String userName, String avatar) {

		if (userName.contains(SUFFIX.USER_SUFFIX)) {
			ServiceResult<UserAO> issql = null;
			String un = userName.substring(0, userName.indexOf("@"));
			issql = userService.getById(un);
			if (issql.getData() == null) {

				return false;
			}
			UserAO ua = issql.getData();
			ua.setAvatar(avatar);
			userService.update(ua);
		}

		if (userName.contains(SUFFIX.APP_SUFFIX)) {
			ServiceResult<ApplicationAO> issql = null;
			String an = userName.substring(0, userName.indexOf("@"));
			issql = applicationService.getById(an);
			ApplicationAO aa = issql.getData();
			aa.setAvatar(avatar);
			applicationService.update(aa);
		}
		
		
		return true;
	}

	@RequestMapping(value = "/setUserInfo", method = { RequestMethod.POST })
	@ResponseBody
	public Object setUserInfo(Model model, HttpServletRequest request) {
	
		Map m = model.asMap();
		return model;
	}

	@RequestMapping(value = "/getWelcomeImg", method = { RequestMethod.POST })
	@ResponseBody
	public Object getWelcomeImg(Model model, HttpServletRequest request) {
		LOG.info("有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(), request.getHeader("user-agent"));
		LOG.info("SessionId %s", request.getSession().getId());

		return null;
	}
	public static String call(final String URL, final String METHOD) {
        String result = null;
        HttpURLConnection conn = null;
        try {
            URL target = new URL(URL);
            conn = (HttpURLConnection) target.openConnection();
            conn.setRequestMethod(METHOD);
            conn.setRequestProperty("Accept", "application/json");
            if (200 != conn.getResponseCode()) {
               result="连接接口出现错误";
            }
            byte[] temp = new byte[conn.getInputStream().available()];
            if (conn.getInputStream().read(temp) != -1) {
                result = new String(temp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return result;
    }
	/**离线统一接口
	 * 
	 * 
	 * @param req
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOfflineText", method = { RequestMethod.POST })
	@ResponseBody
	public Object getOfflineText(@RequestBody OffLineTextRequest req, Model model, HttpServletRequest request) throws Exception {
		 LOG.info("进入getOfflineText接口，有访问来自，IP: %s USER-AGENT: %s", request.getRemoteAddr(),
				 request.getHeader("user-agent"));
		 		OfflineResponse resp = new OfflineResponse();
				BaseResponse baseResponse = new BaseResponse();
				String urlPost=req.getUrlPost();
				String urlGet1=req.getUrlGet1();
				String urlGet2=req.getUrlGet2();
				String urlGet3=req.getUrlGet3();
				String urlGet4=req.getUrlGet4();
				String urlGet5=req.getUrlGet5();
				int i=urlGet1.lastIndexOf("=");
				String bdz=urlGet1.substring(i+1);
				String objIdGetKey ="wiztalk_"+bdz;
				
				String  orgId =req.getOrgId();
				String orgidKey="wiztalk_org_"+orgId;
				String objId_1 = req.getObjId();
				String objIdKey ="wiztalk_"+objId_1;
				String orgIdResult = "";
				
				String totalResult="";
				String result1="";
				String result_2="";
				String result_3="";
				String resultGet1="";
				String resultGet2="";
				String resultGet3="";
				String resultGet4="";
				String resultGet5="";
				String objidList="";
				String realData1="";
				if (redisUtilService.exists(objIdKey)&&redisUtilService.exists(objIdGetKey)&&redisUtilService.exists(orgidKey)) {
					 LOG.info(objIdKey+ " key存在，从redis缓存中获取数据:");
					 orgIdResult=redisUtilService.get(orgidKey);
					 
					 List<String> objIdKeyPostList = redisUtilService.lrange(objIdKey, 0, 2);
						realData1 =objIdKeyPostList.get(2);
						// System.err.println("==========="+realData1.length());
						String realData2 = objIdKeyPostList.get(1);
						String realData3 = objIdKeyPostList.get(0);
						
						
						List<String> objIdKeyGetList = redisUtilService.lrange(objIdGetKey, 0, 4);
						resultGet1=objIdKeyGetList.get(4);
						resultGet2=objIdKeyGetList.get(3);
						resultGet3=objIdKeyGetList.get(2);
						resultGet4=objIdKeyGetList.get(1);
						resultGet5=objIdKeyGetList.get(0);
						
							resp.getAllAssociationObjectsResult1=orgIdResult;
							resp.getPropertiesByObjIdsResult2=realData2;
							resp.getObjRelativeFilesResult3=realData3;
						 	resp.getReult4=resultGet1;
							resp.getReult5=resultGet2;
							resp.getReult6=resultGet3;
							resp.getReult7=resultGet4;
							resp.getReult8=resultGet5;
							
							if (objIdKeyGetList.size()==5&&objIdKeyPostList.size()==3) {
								//
								baseResponse.setRet(BaseResponse.RET_SUCCESS);
								resp.setBaseResponse(baseResponse);
								return resp;
							}else {
								redisUtilService.set(WZTK_puer_offline_data, objIdKeyGetList.size()+objIdKeyPostList.size()+"");
								LOG.info("WZTK_puer_offline_data = :"+ redisUtilService.get(WZTK_puer_offline_data));
								baseResponse.setErrMsg("数据写入失败");
								baseResponse.setRet(BaseResponse.RET_ERROR);
								resp.setBaseResponse(baseResponse);
								return resp;
							}
							
					}else {
						LOG.info(objIdKey+ " 缓存key不存在，調用第三方接口从服务器中获取数据:");
						
						String realData2 = "";
						String realData3="";
						
						if (!redisUtilService.exists(orgidKey)) {
							String data=HttpClientUtil.httpPost(urlPost, ReserverJsonUtil.getJsonObject1(orgId), 10000, 50000);
							Map< String, String> mapOrgid = new HashMap<>();
							mapOrgid=ReserverJsonUtil.readJsonGetResult(data);
							String result=mapOrgid.get("Result");
							 orgIdResult=mapOrgid.get("RealData");
							 totalResult=result;
							 LOG.info("接口orgid调用结果 totalResult= :"+ totalResult);
						}else {
							orgIdResult=redisUtilService.get(orgidKey);
							totalResult="0";
						}
						
						
						if (!redisUtilService.exists(objIdKey)) {
						
						result1=HttpClientUtil.httpPost(urlPost, ReserverJsonUtil.getJsonObject1(objId_1), 10000, 50000);
						Map< String, String> map1 = new HashMap<>();
						map1=ReserverJsonUtil.readJsonGetResult(result1);
						String result=map1.get("Result");
						 realData1=map1.get("RealData");
						// System.err.println("==========="+realData1.length());
						totalResult=totalResult+result;
						LOG.info("接口1===调用结果 totalResult= :"+ totalResult);
						//allAssociationObjects = NodeUtilForObjids.getAllAssociationObjects(realData1, objId_1);
						objidList=NodeUtilForObjids.getProIds(realData1, objId_1);
						if (objidList.length()>20) {
						String result2 = HttpClientUtil.httpPost(urlPost, GetJosonUtil.getPropertiesByObjIds(objidList), 10000, 50000);
						Map< String, String> map2 = new HashMap<>();
						map2=ReserverJsonUtil.readJsonGetResult(result2);
						 result_2=map2.get("Result");
						 realData2=map2.get("RealData");
						totalResult=totalResult+result_2;
						LOG.info("接口2===调用结果 totalResult= :"+ totalResult);
						String reuslt3 = HttpClientUtil.httpPost(urlPost, GetJosonUtil.getObjRelativeFiles(objidList), 10000, 50000);	
						Map< String, String> map3 = new HashMap<>();
						map3=ReserverJsonUtil.readJsonGetResult(reuslt3);
						result_3=map3.get("Result");
						 realData3=map3.get("RealData");
						totalResult=totalResult+result_3;
						}else {
							totalResult="";
						}
						
						LOG.info("接口3===调用结果 totalResult= :"+ totalResult);
						}else {
							LOG.info(objIdKey+"   post緩衝存在，調用緩存 :");
							List<String> objIdKeyPostList = redisUtilService.lrange(objIdKey, 0, 2);
							realData1 =objIdKeyPostList.get(2);
							realData2 = objIdKeyPostList.get(1);
							realData3 = objIdKeyPostList.get(0);
							totalResult="000"+totalResult;
						}
						
						if (!redisUtilService.exists(objIdGetKey)) {
							resultGet1=HttpClientUtil.sendGet(urlGet1,20000,50000);
							resultGet2=HttpClientUtil.sendGet(urlGet2,20000,50000);
							resultGet3=HttpClientUtil.sendGet(urlGet3,10000,50000);
							resultGet4=HttpClientUtil.sendGet(urlGet4,10000,50000);
							resultGet5=HttpClientUtil.sendGet(urlGet5,10000,50000);
							if (!resultGet1.isEmpty()&&!resultGet1.isEmpty()&&!resultGet1.isEmpty()&&!resultGet1.isEmpty()&&!resultGet1.isEmpty()&&!resultGet1.isEmpty()) {
								totalResult=totalResult+"00000";
							}
						}else {
							
							LOG.info(objIdGetKey+"   get緩衝存在，調用緩存 :");
							List<String> objIdKeyGetList = redisUtilService.lrange(objIdGetKey, 0, 4);
							resultGet1=objIdKeyGetList.get(4);
							resultGet2=objIdKeyGetList.get(3);
							resultGet3=objIdKeyGetList.get(2);
							resultGet4=objIdKeyGetList.get(1);
							resultGet5=objIdKeyGetList.get(0);
							totalResult=totalResult+"00000";
						}
						
						resp.getAllAssociationObjectsResult1=orgIdResult;
						resp.getPropertiesByObjIdsResult2=realData2;
						resp.getObjRelativeFilesResult3=realData3;
					 	resp.getReult4=resultGet1;
						resp.getReult5=resultGet2;
						resp.getReult6=resultGet3;
						resp.getReult7=resultGet4;
						resp.getReult8=resultGet5;
						LOG.info("接口调用结果 totalResult= :"+ totalResult);
						if ("000000000".equalsIgnoreCase(totalResult)) {
							if (!redisUtilService.exists(orgidKey)) {
								redisUtilService.set(orgidKey, orgIdResult);
							}
							
							if (!redisUtilService.exists(objIdKey)) {
								redisUtilService.lpush(objIdKey, realData1);
								redisUtilService.lpush(objIdKey, realData2);	
								redisUtilService.lpush(objIdKey, realData3);
							}
							
							//如果key不存在數據，戝存到redis中
							if (!redisUtilService.exists(objIdGetKey)) {
								redisUtilService.lpush(objIdGetKey, resultGet1);
								redisUtilService.lpush(objIdGetKey, resultGet2);
								redisUtilService.lpush(objIdGetKey, resultGet3);
								redisUtilService.lpush(objIdGetKey, resultGet4);
								redisUtilService.lpush(objIdGetKey, resultGet5);
							}
							//設置自動消亡時間，這個時間以後可以以後在後臺自動配置。
							redisUtilService.expire(WZTK_puer_offline_data, 259100);
							redisUtilService.expire(orgidKey, 259200);
							redisUtilService.expire(objIdKey, 259200);
							redisUtilService.expire(objIdGetKey, 259200);
							baseResponse.setRet(BaseResponse.RET_SUCCESS);
							resp.setBaseResponse(baseResponse);
							return resp;
						}else {
							LOG.info("WZTK_puer_offline_data = :"+ redisUtilService.get(WZTK_puer_offline_data));
							baseResponse.setErrMsg("数据写入失败");
							baseResponse.setRet(BaseResponse.RET_ERROR);
							resp.setBaseResponse(baseResponse);
							return resp;
						}
						
						
						}
				
	}
	

	@RequestMapping(value = "/getApplication/{appId}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getApplication(@PathVariable("appId") String appId,HttpServletRequest request) {
		GetApplicationListResponse resp = new GetApplicationListResponse();
		BaseResponse baseResponse = new BaseResponse();
		List<Member> members = new ArrayList<Member>();

		ApplicationCriteria applicationCriteria = new ApplicationCriteria();
		applicationCriteria.createCriteria().andStatusEqualTo(0).andIdEqualTo(appId);
		ServiceResult<List<ApplicationAO>> appListRet = applicationService.selectByCriteria(applicationCriteria);
		if (appListRet.isSucceed() && !CollectionUtils.isEmpty(appListRet.getData())) {
			for (ApplicationAO app : appListRet.getData()) {
				app.setFollow("0");
				members.add(appToMember(app));
			}
		}
		resp.memberList = members;
		resp.memberCount = members.size();
		baseResponse.setRet(BaseResponse.RET_SUCCESS);
		resp.setBaseResponse(baseResponse);
		return resp;
	}
	
	
}

