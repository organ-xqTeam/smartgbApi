package com.smart.socket.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.smart.socket.bean.domain.GarbageBinClean;
import com.smart.socket.provider.GarbageBinCleanProvider;

public interface GarbageBinCleanMapper {

	@Insert("insert into smart_garbagebin_clean(dtu_id,garbage_many,position,clean_date,create_date,del_flag)"
			+ " values(#{dtu_id},#{garbage_many},#{position},#{clean_date},#{create_date},#{del_flag})")
	int insertGbClean(GarbageBinClean record);
	
	@InsertProvider(type = GarbageBinCleanProvider.class, method = "insertAll")
	@Options(useGeneratedKeys=true)
	int batchInsert(@Param("list")List<GarbageBinClean> gbInfo);
	
}