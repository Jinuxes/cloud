package com.jinuxes.cloud.entity;

import java.util.ArrayList;
import java.util.List;

public class FileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FileExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
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

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("file_id like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("file_id not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(Long value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Long value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Long value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Long value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Long value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Long> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Long> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Long value1, Long value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Long value1, Long value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andPathIsNull() {
            addCriterion("path is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            addCriterion("path is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(String value) {
            addCriterion("path =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(String value) {
            addCriterion("path <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThan(String value) {
            addCriterion("path >", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThanOrEqualTo(String value) {
            addCriterion("path >=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThan(String value) {
            addCriterion("path <", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThanOrEqualTo(String value) {
            addCriterion("path <=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLike(String value) {
            addCriterion("path like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(String value) {
            addCriterion("path not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(List<String> values) {
            addCriterion("path in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(List<String> values) {
            addCriterion("path not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(String value1, String value2) {
            addCriterion("path between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(String value1, String value2) {
            addCriterion("path not between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeIsNull() {
            addCriterion("modi_time is null");
            return (Criteria) this;
        }

        public Criteria andModiTimeIsNotNull() {
            addCriterion("modi_time is not null");
            return (Criteria) this;
        }

        public Criteria andModiTimeEqualTo(String value) {
            addCriterion("modi_time =", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeNotEqualTo(String value) {
            addCriterion("modi_time <>", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeGreaterThan(String value) {
            addCriterion("modi_time >", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeGreaterThanOrEqualTo(String value) {
            addCriterion("modi_time >=", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeLessThan(String value) {
            addCriterion("modi_time <", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeLessThanOrEqualTo(String value) {
            addCriterion("modi_time <=", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeLike(String value) {
            addCriterion("modi_time like", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeNotLike(String value) {
            addCriterion("modi_time not like", value, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeIn(List<String> values) {
            addCriterion("modi_time in", values, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeNotIn(List<String> values) {
            addCriterion("modi_time not in", values, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeBetween(String value1, String value2) {
            addCriterion("modi_time between", value1, value2, "modiTime");
            return (Criteria) this;
        }

        public Criteria andModiTimeNotBetween(String value1, String value2) {
            addCriterion("modi_time not between", value1, value2, "modiTime");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryIsNull() {
            addCriterion("is_directory is null");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryIsNotNull() {
            addCriterion("is_directory is not null");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryEqualTo(Boolean value) {
            addCriterion("is_directory =", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryNotEqualTo(Boolean value) {
            addCriterion("is_directory <>", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryGreaterThan(Boolean value) {
            addCriterion("is_directory >", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_directory >=", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryLessThan(Boolean value) {
            addCriterion("is_directory <", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryLessThanOrEqualTo(Boolean value) {
            addCriterion("is_directory <=", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryIn(List<Boolean> values) {
            addCriterion("is_directory in", values, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryNotIn(List<Boolean> values) {
            addCriterion("is_directory not in", values, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryBetween(Boolean value1, Boolean value2) {
            addCriterion("is_directory between", value1, value2, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_directory not between", value1, value2, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andShareIsNull() {
            addCriterion("share is null");
            return (Criteria) this;
        }

        public Criteria andShareIsNotNull() {
            addCriterion("share is not null");
            return (Criteria) this;
        }

        public Criteria andShareEqualTo(Boolean value) {
            addCriterion("share =", value, "share");
            return (Criteria) this;
        }

        public Criteria andShareNotEqualTo(Boolean value) {
            addCriterion("share <>", value, "share");
            return (Criteria) this;
        }

        public Criteria andShareGreaterThan(Boolean value) {
            addCriterion("share >", value, "share");
            return (Criteria) this;
        }

        public Criteria andShareGreaterThanOrEqualTo(Boolean value) {
            addCriterion("share >=", value, "share");
            return (Criteria) this;
        }

        public Criteria andShareLessThan(Boolean value) {
            addCriterion("share <", value, "share");
            return (Criteria) this;
        }

        public Criteria andShareLessThanOrEqualTo(Boolean value) {
            addCriterion("share <=", value, "share");
            return (Criteria) this;
        }

        public Criteria andShareIn(List<Boolean> values) {
            addCriterion("share in", values, "share");
            return (Criteria) this;
        }

        public Criteria andShareNotIn(List<Boolean> values) {
            addCriterion("share not in", values, "share");
            return (Criteria) this;
        }

        public Criteria andShareBetween(Boolean value1, Boolean value2) {
            addCriterion("share between", value1, value2, "share");
            return (Criteria) this;
        }

        public Criteria andShareNotBetween(Boolean value1, Boolean value2) {
            addCriterion("share not between", value1, value2, "share");
            return (Criteria) this;
        }

        public Criteria andTrashIsNull() {
            addCriterion("trash is null");
            return (Criteria) this;
        }

        public Criteria andTrashIsNotNull() {
            addCriterion("trash is not null");
            return (Criteria) this;
        }

        public Criteria andTrashEqualTo(Integer value) {
            addCriterion("trash =", value, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashNotEqualTo(Integer value) {
            addCriterion("trash <>", value, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashGreaterThan(Integer value) {
            addCriterion("trash >", value, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashGreaterThanOrEqualTo(Integer value) {
            addCriterion("trash >=", value, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashLessThan(Integer value) {
            addCriterion("trash <", value, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashLessThanOrEqualTo(Integer value) {
            addCriterion("trash <=", value, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashIn(List<Integer> values) {
            addCriterion("trash in", values, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashNotIn(List<Integer> values) {
            addCriterion("trash not in", values, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashBetween(Integer value1, Integer value2) {
            addCriterion("trash between", value1, value2, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashNotBetween(Integer value1, Integer value2) {
            addCriterion("trash not between", value1, value2, "trash");
            return (Criteria) this;
        }

        public Criteria andTrashByIsNull() {
            addCriterion("trash_by is null");
            return (Criteria) this;
        }

        public Criteria andTrashByIsNotNull() {
            addCriterion("trash_by is not null");
            return (Criteria) this;
        }

        public Criteria andTrashByEqualTo(String value) {
            addCriterion("trash_by =", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByNotEqualTo(String value) {
            addCriterion("trash_by <>", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByGreaterThan(String value) {
            addCriterion("trash_by >", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByGreaterThanOrEqualTo(String value) {
            addCriterion("trash_by >=", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByLessThan(String value) {
            addCriterion("trash_by <", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByLessThanOrEqualTo(String value) {
            addCriterion("trash_by <=", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByLike(String value) {
            addCriterion("trash_by like", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByNotLike(String value) {
            addCriterion("trash_by not like", value, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByIn(List<String> values) {
            addCriterion("trash_by in", values, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByNotIn(List<String> values) {
            addCriterion("trash_by not in", values, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByBetween(String value1, String value2) {
            addCriterion("trash_by between", value1, value2, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashByNotBetween(String value1, String value2) {
            addCriterion("trash_by not between", value1, value2, "trashBy");
            return (Criteria) this;
        }

        public Criteria andTrashTimeIsNull() {
            addCriterion("trash_time is null");
            return (Criteria) this;
        }

        public Criteria andTrashTimeIsNotNull() {
            addCriterion("trash_time is not null");
            return (Criteria) this;
        }

        public Criteria andTrashTimeEqualTo(String value) {
            addCriterion("trash_time =", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeNotEqualTo(String value) {
            addCriterion("trash_time <>", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeGreaterThan(String value) {
            addCriterion("trash_time >", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeGreaterThanOrEqualTo(String value) {
            addCriterion("trash_time >=", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeLessThan(String value) {
            addCriterion("trash_time <", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeLessThanOrEqualTo(String value) {
            addCriterion("trash_time <=", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeLike(String value) {
            addCriterion("trash_time like", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeNotLike(String value) {
            addCriterion("trash_time not like", value, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeIn(List<String> values) {
            addCriterion("trash_time in", values, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeNotIn(List<String> values) {
            addCriterion("trash_time not in", values, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeBetween(String value1, String value2) {
            addCriterion("trash_time between", value1, value2, "trashTime");
            return (Criteria) this;
        }

        public Criteria andTrashTimeNotBetween(String value1, String value2) {
            addCriterion("trash_time not between", value1, value2, "trashTime");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
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