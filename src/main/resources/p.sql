
CREATE  PROCEDURE `seckill`.`execute_seckill`
(in v_seckill_id bigint ,in v_phone bigint,in v_kill_time timestamp,out r_result int)
BEGIN
	declare insert_count int DEFAULT 0;
	start TRANSACTION;
	INSERT ignore into success_killed (seckill_id,user_phone,create_time,state)
	VALUES(v_seckill_id,v_phone,v_kill_time,1);
	select row_count() into insert_count;
	if(insert_count =0) then
	rollback;
	set r_result=-1;
	elseif(insert_count <0) then
	rollback;
	set r_result=-2;
	else 
	UPDATE seckill SET number=number-1 where seckill_id=v_seckill_id 
	and start_time <v_kill_time
	and end_time>v_kill_time
	and number >0;
	select row_count() into insert_count;
	if(insert_count =0) then	
	rollback;
	set r_result=0;
	elseif(insert_count <0) then
	rollback;
	set r_result=-2;
	else 
	set  r_result=1;
	commit;
	end if;
	 end if;
	END
