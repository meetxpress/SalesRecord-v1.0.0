<?php
	$res=array();
	if(isset ($_POST['emp_id']) && ($_POST['pi_lat']) && ($_POST['pi_long'])){
		//&& ($_POST['pi_date']) && ($_POST['pi_time']) 
		
		/*
		?emp_id=300001&pi_lat=37.4219983&pi_long=-122.08400
		*/
		$emp_id=$_POST['emp_id'];
		//$pi_date=$_POST['pi_date'];
		//$pi_time=$_POST['pi_lat'];

		$pi_date=date("d/m/Y");
		$pi_time=date("h:i");
		$pi_lat=$_POST['pi_lat'];
		$pi_long=$_POST['pi_long'];
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry1=mysqli_query($con,"select shop_id from employee_master where emp_id='$emp_id'");
		
		if(mysqli_num_rows($qry1) > 0 ){
			$row1 = mysqli_fetch_assoc($qry1);
		}
		
		$shop_id=$row1["shop_id"];
		$qry2=mysqli_query($con,"select shop_locLat,shop_locLong from shop_master where shop_id='$shop_id'");
		
		if(mysqli_num_rows($qry2) > 0 ){
			$row2 = mysqli_fetch_assoc($qry2);
		}
		/*
		echo "<br>Lat user:--> $pi_lat";
		echo "<br>Lat Database:--> ".$row2["shop_locLat"];
		
		echo "<br>Long user:--> $pi_long";
		echo "<br>Long Database:--> ".$row2["shop_locLong"];
		
		echo "<br><br>Lat-Type:==> ".gettype($long_dis);
		echo "<br><br>Laong-Type:==> ".gettype($lat_dis);
		
		*/
		$long_dis=substr((double)$row2["shop_locLat"] - (double)$pi_lat, 0, 5);
		$lat_dis=substr((double)$row2["shop_locLong"] - (double)$pi_long, 0, 5);
		
		if(($long_dis < 200) and ($lat_dis < 200)){
			$qry3=mysqli_query($con,"insert into attendance_management (emp_id,p_date,pi_time) values ('$emp_id','$pi_date','$pi_time')");
		}

		if($qry3){
			$res["success"]=1;
			$res["message"]="punch inserted Successfully.";
		
			echo json_encode($res);
		}else{
			$res["success"]=0;					
			$res["message"]="Can't insert punch details";
			
			echo json_encode($res);
		}					
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>