package com.smart.socket.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.provider.GarbageBinMsgProvider;

public interface GarbageBinMsgMapper {

	@Insert("insert into smart_garbagebin_msg(dtu_id,garbage_state,position_state,latitude,hemisphere_lat,"
			+ "longitude,hemisphere_lgt,err,byte_sum,deal_state,create_date) values(#{dtu_id},#{garbage_state},#{position_state},"
			+ "#{latitude},#{hemisphere_lat},#{longitude},#{hemisphere_lgt},#{err},#{byte_sum},#{deal_state},#{create_date})")
	int insertGbMsg(GarbageBinMsg record);

	@Select("SELECT a.* FROM smart_garbagebin_msg a,(SELECT dtu_id,MAX(id) max_id FROM	smart_garbagebin_msg "
			+ "WHERE deal_state = 0 AND garbage_state != ':55' AND position_state = 'A' AND create_date > #{createDate} "
			+ "GROUP BY dtu_id)b WHERE a.id=b.max_id")
	List<GarbageBinMsg> findWillDealGbMsg(@Param("createDate") String createDate);
	
	@UpdateProvider(type=GarbageBinMsgProvider.class,method="batchUpdateState")
	void batchUpdateState(@Param("list")List<GarbageBinMsg> gbMsgList);
	
	@Update("update smart_garbagebin_msg set deal_state=1 where id=#{id}")
	void updateGbMsgDealState(@Param("id") String id);
	
	@Update({
		"<script>",
		"<foreach item='value' index='key' collection='gbMsgList' separator=';'>",
		"update smart_garbagebin_msg set deal_state=1,update_date=#{value.update_date} where id=#{value.id}",
		"</foreach>",
		"</script>"
	})
	void updateGbMsgDealStateAll(@Param("gbMsgList")List<GarbageBinMsg> gbMsgList);
	
	@Select("SELECT a.* FROM smart_garbagebin_msg a,(SELECT dtu_id,MAX(id) max_id FROM	smart_garbagebin_msg "
			+ "WHERE deal_state = 0 AND garbage_state = '200' AND position_state = 'A' AND create_date > #{createDate} "
			+ "GROUP BY dtu_id)b WHERE a.id=b.max_id")
	List<GarbageBinMsg> findDealGbCleanMsg(@Param("createDate") String createDate);
	
	@Update("update smart_garbagebin_msg set deal_state=1,update_date=#{update_date} where id=#{id}")
	void updateGbMsgByDtu(GarbageBinMsg record);
}