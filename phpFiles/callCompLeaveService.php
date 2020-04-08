<?php
    $res = array();
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $c0=0;        
        $count1 = 0;
        $count0 = 0;

        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry1=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");
        if(mysqli_num_rows($qry1) > 0 ){ 
            while($row0 = mysqli_fetch_assoc($qry1)){
                $c0++;
                $eid = $row0['emp_id'];

                /*
                * getting leaveCount from database
                */
                $qry0=mysqli_query($con,"select * from leave_management where emp_id = '$eid'");
                if(mysqli_num_rows($qry0) > 0 ){ 
                    while($row = mysqli_fetch_assoc($qry0)){
                        $count0++;
                    }
                }
                
                /*
                * getting approved leave from database
                */
                $qry=mysqli_query($con,"select * from leave_management where emp_id = '$eid' and status='Approved'");
                if(mysqli_num_rows($qry) > 0 ){                     
                    //creating json array to store json in array format
                    $res["Leaves"] = array();
                    
                    while($row = mysqli_fetch_assoc($qry)){
                        $duh = $row['fromDate'];
                        $mon = substr($duh, 3);
                        
                        if($_POST['yr'] == $mon){         
                            $count1++;
                        }
                    }

                    if($count1 != 0 ){
                        $lev = array();
                        $lev["emp_id"] = $eid;
                        $lev["l_count"] = $count0;
                        $lev["a_count"] = $count1;
                        
                        $res["success"] = 1;
                        array_push($res["Leaves"], $lev);
                        echo json_encode($res);                        
                    }else{
                        $res["success"] = 0;					
                        $res["message"] = "No leave found";

                        echo json_encode($res);
                    } 
                        //echo "<br>ID: ".$eid."<br>leaves: ". $count0 ." <br> Approved: ". $count1 ." <br><br>";
                    $count1 = 0;
                    $count0 = 0;
                }

            }
        }        
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>