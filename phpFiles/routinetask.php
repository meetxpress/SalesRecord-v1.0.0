<?php
	$res=array();
	if(isset($_POST['emp_id']) && ($_POST['rmonth']) && ($_POST['rtarget'])){
		
		$emp_id=$_POST['emp_id'];
		$rmonth=$_POST['rmonth'];
		$rtarget=$_POST['rtarget'];
		
		
		

		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		
		$qry=mysqli_query($con,"select emonth from employee_master where emp_id='$emp_id'");
        
        if(mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);
			
			
			if($row["emonth"]==$rmonth)
			{
				$qry1=mysqli_query($con,"UPDATE employee_master SET etarget=etarget-'$rtarget' where emp_id='$emp_id'");
				
				$res["success"] = 1;
				$res["message"] = "Data found Successfully.";
				
				echo json_encode($res);
			}
			else{
				$res["success"] = 0;
				$res["message"]="Not match";
    
	
			
            echo json_encode($res);
			}
			
			
		}else{
            $res["success"] = 0;
            $res["message"]="Something went wrong.";
    
	
			
            echo json_encode($res);
        }		
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		
		echo json_encode($res);
	}
	
	//$qry1=mysqli_query($con,"UPDATE employee_master SET etarget=etarget-'$rtarget where emp_id='$emp_id'");
?>

