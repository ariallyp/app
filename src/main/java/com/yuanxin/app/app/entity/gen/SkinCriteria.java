package com.yuanxin.app.app.entity.gen;

import com.yuanxin.framework.mybatis.Page;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SkinCriteria implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table org
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table org
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table org
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table org
     *
     * @mbggenerated
     */
    protected Page page;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table org
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public SkinCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public void setOredCriteria(List<Criteria> oredCriteria) {
        this.oredCriteria = oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public void setPage(Page page) {
        this.page=page;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table org
     *
     * @mbggenerated
     */
    public Page getPage() {
        return page;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table org
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() { //各个字段都会有 下面的  名字为空
            addCriterion("skinName is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() { //名字不为空
            addCriterion("skinName is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) { //与字段名相等
            addCriterion("skinName =", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) { //与字段名不相等
            addCriterion("skinName <>", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) { //大于字段 的值，一般用于数值比较，
            addCriterion("skinName >", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {//大于等于字段 的值，一般用于数值比较，
            addCriterion("skinName >=", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) { //小于字段 的值，一般用于数值比较，
            addCriterion("skinName <", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) { //小于等于字段 的值，一般用于数值比较，
            addCriterion("skinName <=", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) { //模糊匹配，常用，
            addCriterion("skinName like", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) { //not like 一个字段模糊匹配，不常用，
            addCriterion("skinName not like", value, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {// in 查询一个字段，不太常用
            addCriterion("skinName in", values, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {// not in 一个字段
            addCriterion("skinName not in", values, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {// 在一个范围之内，时间字段比较常用
            addCriterion("skinName between", value1, value2, "skinName");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) { // 不在一个时间段之内，时间字段使用，不常用
            addCriterion("skinName not between", value1, value2, "skinName");
            return (Criteria) this;
        }
        // short name 
        public Criteria andShortNameIsNull() {
            addCriterion("skinType is null");
            return (Criteria) this;
        }

        public Criteria andShortNameIsNotNull() {
            addCriterion("skinType is not null");
            return (Criteria) this;
        }

        public Criteria andShortNameEqualTo(String value) {
            addCriterion("skinType =", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameNotEqualTo(String value) {
            addCriterion("skinType <>", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThan(String value) {
            addCriterion("skinType >", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("skinType >=", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThan(String value) {
            addCriterion("skinType <", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThanOrEqualTo(String value) {
            addCriterion("skinType <=", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameLike(String value) {
            addCriterion("skinType like", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameNotLike(String value) {
            addCriterion("skinType not like", value, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameIn(List<String> values) {
            addCriterion("skinType in", values, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameNotIn(List<String> values) {
            addCriterion("skinType not in", values, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameBetween(String value1, String value2) {
            addCriterion("skinType between", value1, value2, "skinType");
            return (Criteria) this;
        }

        public Criteria andShortNameNotBetween(String value1, String value2) {
            addCriterion("skinType not between", value1, value2, "skinType");
            return (Criteria) this;
        }

        public Criteria andDiviceTypeIsNull() {
            addCriterion("diviceType is null");
            return (Criteria) this;
        }

        public Criteria andDiviceTypeIsNotNull() {
            addCriterion("diviceType is not null");
            return (Criteria) this;
        }

        public Criteria andDiviceTypeEqualTo(String value) {
            addCriterion("diviceType =", value, "diviceType");
            return (Criteria) this;
        }

   

        public Criteria andLocationIsNull() {
            addCriterion("skinUrl is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("skinUrl is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(String value) {
            addCriterion("skinUrl =", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(String value) {
            addCriterion("skinUrl <>", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(String value) {
            addCriterion("skinUrl >", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(String value) {
            addCriterion("skinUrl >=", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(String value) {
            addCriterion("skinUrl <", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(String value) {
            addCriterion("skinUrl <=", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationLike(String value) {
            addCriterion("skinUrl like", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationNotLike(String value) {
            addCriterion("skinUrl not like", value, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<String> values) {
            addCriterion("skinUrl in", values, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<String> values) {
            addCriterion("skinUrl not in", values, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(String value1, String value2) {
            addCriterion("skinUrl between", value1, value2, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(String value1, String value2) {
            addCriterion("skinUrl not between", value1, value2, "skinUrl");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("skinAvatar is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("skinAvatar is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(String value) {
            addCriterion("skinAvatar =", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(String value) {
            addCriterion("skinAvatar <>", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(String value) {
            addCriterion("skinAvatar >", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(String value) {
            addCriterion("skinAvatar >=", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(String value) {
            addCriterion("skinAvatar <", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(String value) {
            addCriterion("skinAvatar <=", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdLike(String value) {
            addCriterion("skinAvatar like", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotLike(String value) {
            addCriterion("skinAvatar not like", value, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<String> values) {
            addCriterion("skinAvatar in", values, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<String> values) {
            addCriterion("skinAvatar not in", values, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(String value1, String value2) {
            addCriterion("skinAvatar between", value1, value2, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(String value1, String value2) {
            addCriterion("skinAvatar not between", value1, value2, "skinAvatar");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table org
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table org
     *
     * @mbggenerated
     */
    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}