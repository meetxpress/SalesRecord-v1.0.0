<?php
    $res = array();
    $c0=0;
	
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  					
        $qry0=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");         			
					
		if($qry0){ 				
            $res["success"] = 1;
            $res["message"] = "Salary found Successfully.";
            $res["Salary2"] = array();

            while($row0 = mysqli_fetch_assoc($qry0)){
                $c0++;
                $eid = $row0['emp_id'];
                
                $qry=mysqli_query($con,"select * from emp_sal where emp_id = '$eid' and emp_month ='".$_POST['yr']."' ");                
                if($qry){                  				
                    while($row1 = mysqli_fetch_assoc($qry)){	
                        //$duh = $row['fromDate'];
                        //$mon = substr($duh, 3);					

                        $sal = array(); 
                        $sal["emp_id"] = $eid;
                        $sal["emp_inc"] = $row1["emp_inc"];
                        $sal["emp_totsal"] = $row1["emp_totsal"];
                        $sal["emp_month"] = $row1["emp_month"];
                        array_push($res["Salary2"], $sal);                          
                    }       									
                } else {
                    $res["success"] = 0;					
                    $res["message"] = "No data found";
                             
                    echo json_encode($res);
                }        
            }
           echo json_encode($res);
        }
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>