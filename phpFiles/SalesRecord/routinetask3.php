<?php
	$res=array();
	if(isset($_POST['emp_id']) && ($_POST['rmonth']) && ($_POST['rtarget']) && ($_POST['rdate'])){
		
		/*
		?emp_id=300001&rmonth=04-2020&rtarget=20&rdate:12-04-2020
		*/

		$emp_id=$_POST['emp_id'];
		$rmonth=$_POST['rmonth'];
		$rtarget=$_POST['rtarget'];
		$rdate=$_POST['rdate'];
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		$qry1=mysqli_query($con,"select comp_id from employee_master where emp_id='$emp_id'");
		
		if(mysqli_num_rows($qry1) > 0 ){
			$row1 = mysqli_fetch_assoc($qry1);
		}
		$comp_id=$row1['comp_id'];
		
		
		$q1=mysqli_query($con,"select p_month,p_target from emp_ptarget where emp_id='$emp_id'");
		if(mysqli_num_rows($q1) > 0 ){
			$r1 = mysqli_fetch_assoc($q1);
			if(($r1["p_month"]==$rmonth) and ($r1["p_target"] > 0))
			{
				$q2=mysqli_query($con,"UPDATE emp_ptarget SET p_target=p_target-'$rtarget' where emp_id='$emp_id' and p_month='$rmonth' and comp_id='$comp_id'");
				if($q2){
					$res["success"] = 1;
					$res["message"] = "Work submitted Successfully.";
					
					echo json_encode($res);
				}else{
					$res["success"] = 0;
					$res["message"]="Error1!!";
    
					echo json_encode($res);
				}
			}else {
				// pending target=0;				
			}
		}
		else
		{
            $res["success"] = 0;
            $res["message"]="Something went wrong.";
    
            echo json_encode($res);
        }
        
		
		$qry=mysqli_query($con,"select atargetmonth,atarget from emp_target where comp_id='$comp_id'");
        if(mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);		
			if(($row["atargetmonth"]==$rmonth) and ($row["atarget"] > 0)){
				$qry1=mysqli_query($con,"UPDATE emp_target SET atarget=atarget-'$rtarget' where comp_id='$comp_id' and atargetmonth='$rmonth'");
				$qry2=mysqli_query($con,"insert into emp_routinetask (emp_id,rt_date,rt_unit) values ('$emp_id','$rdate','$rtarget')");
				
				if($qry1 and $qry2){
					$res["success"] = 1;
					$res["message"] = "Work submitted Successfully.";
					
					echo json_encode($res);
				} else {
					$res["success"] = 0;
					$res["message"]="Error1!!";
    
					echo json_encode($res);
				}
			} else if(($row["atargetmonth"]==$rmonth) and ($row["atarget"] <= 0)) {				
				//$qry1=mysqli_query($con,"UPDATE emp_target SET atarget=atarget+'$rtarget' where comp_id='$comp_id' and atargetmonth='$rmonth'");
				$qry2=mysqli_query($con,"insert into emp_routinetask (emp_id,rt_date,rt_unit) values ('$emp_id','$rdate','$rtarget')");				
				
				if($qry1 and $qry2){
					$res["success"] = 1;
					$res["message"] = "Work uploaded Successfully.";
					
					echo json_encode($res);
				}else{
					$res["success"] = 0;
					$res["message"]="Error!!";
    
					echo json_encode($res);
				}				
			} else {
				$res["success"] = 0;
				$res["message"]="Error AA!!";
    
				echo json_encode($res);
			}			
			
		}else{
            $res["success"] = 0;
            $res["message"]="Something went wrong.";
	
			echo json_encode($res);
        }
		
		//$emp_id=$_POST['emp_id'];
		$smonth=$_POST['rmonth'];
		
		$emp_bsal=10000;
		$emp_inc=500;
		$emp_inc1=0;
		$emp_totsal=10500;
		$emp_totsal1=10000;
		
        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
                
        $qs1=mysqli_query($con,"select p_target,p_month from emp_ptarget where emp_id='$emp_id'");
		
		if(mysqli_num_rows($qs1) > 0 )
		{
			$rs1 = mysqli_fetch_assoc($qs1);
		}
		
		//echo $rs1["p_target"] ."<br>";
		//echo $rs1["p_month"] ."<br>";
		//echo $rmonth ."<br>";
		
		if(($rs1["p_target"] <= 0) and ($rs1["p_month"] == $rmonth)) {						
			$qs2=mysqli_query($con,"update emp_sal set emp_inc='$emp_inc',emp_totsal='$emp_totsal' where emp_id='$emp_id' and emp_month='$smonth'");				
		} else if(($rs1["p_target"] > 0) and ($rs1["p_month"] == $rmonth)) {			
			$qs3=mysqli_query($con,"update emp_sal set emp_inc='$emp_inc1' where emp_id='$emp_id' and emp_month='$smonth'");						
		} else {
			//something went wrong;			
		}		
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";

		echo json_encode($res);
	}
?>