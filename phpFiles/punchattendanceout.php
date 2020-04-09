<?php
	$res=array();
	if(isset ($_POST['emp_id']) && ($_POST['po_lat']) && ($_POST['po_long'])){
		//&& ($_POST['po_date']) && ($_POST['po_time']) &&

		/*
		?emp_id=300001&po_lat=37.4219983&po_long=-122.08400
		*/		
		$emp_id=$_POST['emp_id'];
		//$po_date=$_POST['po_date'];
		//$po_time=$_POST['po_lat'];
		
		$po_date=date("d/m/Y");
		$po_time=date("h:i");
		$po_lat=$_POST['po_lat'];
		$po_long=$_POST['po_long'];
		
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
//		$long_dis=$row2["shop_locLat"]-$po_lat;
//		$lat_dis=$row2["shop_locLong"]-$po_long;

		$long_dis=substr((double)$row2["shop_locLat"] - (double)$po_lat, 0, 5);
		$lat_dis=substr((double)$row2["shop_locLong"] - (double)$po_long, 0, 5);

		if(($long_dis  < 200) and ($lat_dis  < 200)){			
			$qry3=mysqli_query($con,"update attendance_management set po_time='$po_time' where emp_id='$emp_id' and p_date='$po_date'");
		}
		
		if($qry3){
			$res["success"]=1;
			$res["message"]="punch updated Successfully.";
		
			echo json_encode($res);
		}else{
			$res["success"]=0;					
			$res["message"]="Can't update punch details";
			
			echo json_encode($res);
		}						
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>
