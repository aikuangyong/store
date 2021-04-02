<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.${scheml}.dao.${name?cap_first}Mapper">
    <resultMap id="${name?uncap_first}Map" type="com.${scheml}.model.${name?cap_first}Model">
        <#if modelList?? && modelList?size gt 0>
            <#list modelList as modelObj>
                <#if modelObj.isKey?length gt 2>
                    <id column="${modelObj.columnName}" property="${modelObj.property}" javaType="${modelObj.javaType}" />
                <#else>
                    <result column="${modelObj.columnName}" property="${modelObj.property}" javaType="${modelObj.javaType}" />
                </#if>
            </#list>
        </#if>
    </resultMap>

    <select id="getList" parameterType="${name?uncap_first}Model" resultType="${name?uncap_first}Model">
		SELECT
			<#list modelList as modelObj>
                <#if modelObj_index = modelList?size-1 >
                    ${modelObj.columnName}
                <#else>
                    ${modelObj.columnName},
                </#if>
            </#list>
		FROM
            ${name?cap_first} tbl
        where 1=1
        <#list modelList as modelObj>
            <if test="${modelObj.property} != '' and ${modelObj.property} != null ">
                and ${modelObj.columnName} = ${r"#"}{${modelObj.property?lower_case}}
            </if>
        </#list>
        limit ${r"${pageNow}"},${r"${pageSize}"}
	</select>
    
    <select id="getCount" parameterType="${name?uncap_first}Model" resultType="java.lang.Integer">
        SELECT COUNT(1) FROM ${name?cap_first}
        where 1=1
        <#list modelList as modelObj>
            <if test="${modelObj.property} != '' and ${modelObj.property} != null ">
                and ${modelObj.columnName} = ${r"#"}{${modelObj.property}}
            </if>
        </#list>
    </select>

    <update id="update" parameterType="${name?uncap_first}Model" >
        update ${name?cap_first} tbl
        set ${primaryKey} = ${primaryKey}
        <#list modelList as modelObj>
            <#if modelObj.isKey != ''>
            <#else>
            <if test="${modelObj.property} != '' and ${modelObj.property} != null ">
                ,${modelObj.columnName} =${r"#"}{${modelObj.property}}
            </if>
            </#if>
        </#list>
        where ${primaryKey} = ${r"#"}{${primaryKey}}
    </update>

    <update id="disableOrEnable" parameterType="${name?uncap_first}Model">
        update ${name?cap_first} set valid = ${r"${valid}"} where ${primaryKey}
        <foreach collection="idList" item="primaryKey" index="no" open="("
                 separator="," close=")">
            ${r"#"}{primaryKey}
        </foreach>
    </update>

    <delete id="delete" parameterType="${name?uncap_first}Model">
        delete FROM  ${name?cap_first} where
        ${primaryKey} in
        <foreach collection="idList" item="primaryKey" index="no" open="("
                 separator="," close=")">
            ${r"#"}{primaryKey}
        </foreach>
    </delete>

    <insert id="insert" useGeneratedKeys="true" keyProperty="${primaryKey}" parameterType="${name?uncap_first}Model">
        <selectKey  keyProperty="${primaryKey}" resultType="java.lang.String" order="BEFORE">
            select uuid()
        </selectKey>
        insert into ${name?cap_first}
        (
            <#list modelList as modelObj>
                <#if modelObj_index = modelList?size-1 >
                    ${modelObj.columnName}
                <#else>
                    ${modelObj.columnName},
                </#if>
            </#list>
        )
        values
        (
            <#list modelList as modelObj>
                <#if modelObj_index = modelList?size-1 >
                    ${r"#"}{${modelObj.property}}
                <#else>
                    ${r"#"}{${modelObj.property}},
                </#if>
            </#list>
        )
    </insert>
</mapper>