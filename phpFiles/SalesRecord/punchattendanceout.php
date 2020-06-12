<?php
	$res=array();
	if(isset ($_POST['emp_id']) && ($_POST['pi_lat']) && ($_POST['pi_long']) && ($_POST['pi_date']) && ($_POST['pi_time'])){
		//&& ($_POST['pi_date']) && ($_POST['pi_time']) 
		
		/*
		?emp_id=300001&pi_lat=37.4219983&pi_long=-122.08400
		*/
		$emp_id=$_POST['emp_id'];
		$pi_date=$_POST['pi_date'];
		$pi_time=$_POST['pi_time'];

		//$pi_date=date("d/m/Y");
		//$pi_time=date("h:i");
		$pi_lat=$_POST['pi_lat'];
		$pi_long=$_POST['pi_long'];
		$long_dis="";
		$lat_dis="";
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		
		$qry1=mysqli_query($con,"select shop_id from employee_master where emp_id='$emp_id'");		
		if(mysqli_num_rows($qry1) > 0 ){
			$row1 = mysqli_fetch_assoc($qry1);
		}
		
		$shop_id=$row1["shop_id"];
		
		$q1=mysqli_query($con,"select po_time from attendance_management where emp_id='$emp_id' and p_date='$pi_date'");		
		if(mysqli_num_rows($q1) > 0){
			$r1 = mysqli_fetch_assoc($q1);
			
			if($r1["po_time"]==""){
				$qry2=mysqli_query($con,"select shop_locLat,shop_locLong from shop_master where shop_id='$shop_id'");
			
				if(mysqli_num_rows($qry2) > 0 ){
					$row2 = mysqli_fetch_assoc($qry2);
				}
					
				$long_dis=strcmp($row2["shop_locLong"],$pi_long);
				$lat_dis=strcmp($row2["shop_locLat"],$pi_lat);
				//echo $long_dis;
				//echo $lat_dis;
			
				if(($long_dis < 5) && ($lat_dis < 5) && ($long_dis > -5) && ($lat_dis > -5)) {
					$qry3=mysqli_query($con,"update attendance_management set po_time='$pi_time' where emp_id='$emp_id' and p_date='$pi_date'");
					
					if($qry3){
					$res["success"]=1;
					$res["message"]="punch updated Successfully.";
				
					echo json_encode($res);
					}
				}else{
					$res["success"]=0;					
					$res["message"]="Can't updated punch details";
					
					echo json_encode($res);
				}
			
			} else {
				$res["success"]=1;
				$res["message"]="Data Exists!!";
				echo json_encode($res);
			}										
		} else {
			$res["success"]=1;
			$res["message"]="Something went wrong!!";
			echo json_encode($res);
		}									
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}	
?>