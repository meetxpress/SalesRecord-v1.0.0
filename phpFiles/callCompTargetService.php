<?php
    $res = array();
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry=mysqli_query($con,"select atarget,atargetmonth from emp_target where comp_id = '".$_POST['comp_id']."'");
        $qry2=mysqli_query($con,"select p_target,emp_id from emp_ptarget where comp_id = '".$_POST['comp_id']."' and p_month='".$_POST['yr']."'");
    
        if((mysqli_num_rows($qry) > 0 ) and (mysqli_num_rows($qry2) > 0 )){ 
            //creating json array to store json in array format
            $res["Target"] = array();

            while(($row = mysqli_fetch_assoc($qry)) and ($row2 = mysqli_fetch_assoc($qry2)) ){
                $mon = $row['atargetmonth'];

                $tar = array();
                $tar["mon"] = $mon;
                $tar["atarget"] = $row["atarget"];
                $tar["p_target"] = $row2["p_target"];

                if($_POST['yr'] == $mon){                    
                    $res["success"] = 1;
                    $res["message"] = "Salary found Successfully.";
                    
                    array_push($res["Target"], $tar);
                }
            }
            echo json_encode($res);			
		}else{
			$res["success"] = 0;					
			$res["message"] = "No data found";
					 
			echo json_encode($res);
        }	
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>