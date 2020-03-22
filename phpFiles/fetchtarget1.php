<?php
    $res = array();
    if(isset($_POST['emp_id'])&& ($_POST['cmonth'])){

		$emp_id=$_POST['emp_id'];
		$cmonth=$_POST['cmonth'];
		//$emp_id="300001";
        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        
		$qry1=mysqli_query($con,"select comp_id from employee_master where emp_id='$emp_id'");
		
		if(mysqli_num_rows($qry1) > 0 ){
			$row1 = mysqli_fetch_assoc($qry1);
		}
		
		$comp_id=$row1['comp_id'];
	
	
		$q1=mysqli_query($con,"select p_month from emp_ptarget where emp_id='$emp_id'");
		
		if(mysqli_num_rows($q1) > 0 )
		{
			$r1= mysqli_fetch_assoc($q1);
			
			$q3=mysqli_query($con,"select p_target from emp_ptarget where emp_id='$emp_id' and p_month='$cmonth'");
			
			if( mysqli_num_rows($q3) > 0)
			{
				$r3 = mysqli_fetch_assoc($q3);
			
				$res["success"] = 1;
				$res["message"] = "Data found Successfully.";
			
				$res["p_target"] = $r3["p_target"];
			
				
				echo json_encode($res);
			}
			else
			{
				$res["success"] = 0;
				$res["message"]="Something went wrong.";
    
				
				echo json_encode($res);
			}
		}
		else
		{
			// $q2=mysqli_query($con,"select p_target from emp_target where comp_id='$comp_id' and atargetmonth='$cmonth'");
        
			// if(mysqli_num_rows($q2) > 0)
			// {
				// $r2 = mysqli_fetch_assoc($q2);
			
				
				// $res["success"] = 1;
				// $res["message"] = "Data found Successfully.";
			
				// $res["p_target"] = $r2["p_target"];
			
				
				// echo json_encode($res);
			// }
			// else
			// {
				// $res["success"] = 0;
				// $res["message"]="Something went wrong.";
				
				
				// echo json_encode($res);
			// }
		}
		
    }else{
        $res["success"]=0;
        $res["message"] = "Required Field(s) is(are) missing.";

        echo json_encode($res);
    }
?>