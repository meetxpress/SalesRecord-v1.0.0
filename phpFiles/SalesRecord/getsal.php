<?php
    $res = array();
    if(isset($_POST['emp_id']) && ($_POST['smonth'])){

		$con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
		$emp_id=$_POST['emp_id'];
		$smonth=$_POST['smonth'];
		
		/*$emp_bsal=10000;
		$emp_inc=500;
		$emp_inc1=0;
		$emp_totsal=15000;
		$emp_totsal1=10000;
		
        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        //$qry=mysqli_query($con,"select esal from employee_master where emp_id='$emp_id'");
        
        $q1=mysqli_query($con,"select p_target,p_month from emp_ptarget where emp_id='$emp_id'");
		
		if(mysqli_num_rows($q1) > 0 )
		{
			$r1 = mysqli_fetch_assoc($q1);
		}
		
		
		if(($r1["p_target"] == 0) and ($r1["p_month"] == $smonth))
		{
			$q2=mysqli_query($con,"insert into emp_sal (emp_id,emp_bsal,emp_inc,emp_totsal) values ('$emp_id','$emp_bsal','$emp_inc1','$emp_totsal1')");
			
			
			
		}
		else if(($r1["p_target"] > 0) and ($r1["p_month"] == $smonth))
		{
			$q3=mysqli_query($con,"insert into emp_sal (emp_id,emp_bsal,emp_inc,emp_totsal) values ('$emp_id','$emp_bsal','$emp_inc','$emp_totsal')");
			
		}
		else
		{
			//something went wrong;
			
		} */
		
		$q4=mysqli_query($con,"select emp_bsal,emp_inc,emp_totsal from emp_sal where emp_id='$emp_id' and emp_month='$smonth'");
		
		if($q4){
			$r4 = mysqli_fetch_assoc($q4);
			
			$res["success"] = 1;
			$res["message"] = "Data found Successfully.";
			
			$res["emp_bsal"] = $r4["emp_bsal"];
			$res["emp_inc"] = $r4["emp_inc"];
			$res["emp_totsal"] =  $r4["emp_totsal"];
			
			echo json_encode($res);
		}else{
			
            $res["success"] = 0;
            $res["message"]="Default Salary";
			
			$res["emp_bsal"] = 10000;
			$res["emp_inc"] = 0;
			$res["emp_totsal"] = 10000;
    
            echo json_encode($res);
        }
    }else{
        $res["success"]=0;
        $res["message"] = "Required Field(s) is(are) missing.";

        echo json_encode($res);
    }
?>