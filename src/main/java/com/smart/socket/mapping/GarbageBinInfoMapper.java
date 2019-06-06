package com.smart.socket.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.smart.socket.bean.domain.GarbageBinInfo;
import com.smart.socket.provider.GarbageBinInfoProvider;

public interface GarbageBinInfoMapper {

	@Insert("insert into smart_garbagebin_info(dtu_id,garbage_many,garbage_total,position,latitude,"
			+ "longitude,connect_state,monitor_state,del_flag,gps_state,create_date) values(#{dtu_id},"
			+ "#{garbage_many},#{garbage_total},#{position},#{latitude},#{longitude},#{connect_state},"
			+ "#{monitor_state},#{del_flag},#{gps_state},#{create_date})")
	int insert(GarbageBinInfo gbInfo);
	
	@Select("select * from smart_garbagebin_info where del_flag=0 and dtu_id=#{dtu_id}")
	GarbageBinInfo getGbInfoByDtuId(@Param("dtu_id") String dtuId);

	@InsertProvider(type = GarbageBinInfoProvider.class, method = "insertAll")
	int batchInsert(@Param("list")List<GarbageBinInfo> gbInfo);
	
	@Select("select * from smart_garbagebin_info where del_flag=0")
	List<GarbageBinInfo> selGbInfoAll();
	
	@Select("select dtu_id from smart_garbagebin_info where del_flag=0")
	List<String> selGbInfoAllId();
	
	@Update("update smart_garbagebin_info set del_flag=1 where dtu_id=#{dtu_id}")
	void updateGbInfoDelFlag(@Param("dtu_id") String dtu_id);
	
	@UpdateProvider(type = GarbageBinInfoProvider.class, method = "updateAll")
	void updateGbInfoAll(@Param("list")List<GarbageBinInfo> gbInfo);
	
	/*@Update({
		"<script>",
		"<foreach item='value' index='key' collection='gbInfoList' separator=';'>",
		"update SMART_GARBAGEBIN_INFO set garbage_many=#{value}.garbage_many,connect_state=#{value}.connect_state,"
		+ "monitor_state=#{value}.monitor_state,gps_state=#{value}.gps_state,update_date=#{value}.update_date where dtu_id=#{value}.dtu_id",
		"</foreach>",
		"</script>"
	})
	void updateGbInfoAll2(@Param("gbInfoList")List<GarbageBinInfo> gbInfoList);*/
	
	@Update({
		"<script>",
		"<foreach item='value' index='key' collection='gbInfoList' separator=';'>",
		"update SMART_GARBAGEBIN_INFO set garbage_many=#{value.garbage_many},connect_state=#{value.connect_state},"
		+ "monitor_state=#{value.monitor_state},gps_state=#{value.gps_state},update_date=#{value.update_date} where dtu_id=#{value.dtu_id}",
		"</foreach>",
		"</script>"
	})
	void updateGbInfoAll2(@Param("gbInfoList")List<GarbageBinInfo> gbInfoList);
	
	@Update("update smart_garbagebin_info set garbage_many=#{garbage_many},update_date=#{update_date},connect_state=#{connect_state},"
			+ "monitor_state=#{monitor_state} where dtu_id=#{dtu_id}")
	int updateGbInfoByDtu(GarbageBinInfo gbInfo);
	
	@Update("update smart_garbagebin_info set connect_state=#{connect_state} where dtu_id=#{dtu_id}")
	int updateGbInfoConnectState(@Param("dtu_id") String dtu_id,@Param("connect_state") String connect_state);

}