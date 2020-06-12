<?php
    $res = array();
    $c0=0;    
	
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  					
        $qry0=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");         			
                      
        $mon = substr($_POST['yr'], 0, 2);
        if (($mon % 2 != 0) && ($mon <= 7)) {
            // odd months till July(7)
            $tot_days = 31;
        } else if (($mon % 2 != 0) && ($mon > 7)) {
            //odd months after July(7)
            $tot_days = 30;
        } else if (($mon == 02) && (substr($_POST['yr'], -4) % 4 == 0)) {
            //leap year
            $tot_days = 29;
        } else if (($mon == 02 ) && (substr($_POST['yr'], -4) % 4 != 0)) {
            //february month
            $tot_days = 28;
        } else if (($mon % 2 == 0) && ($mon <= 6)) {
            // even months before July(6)
            $tot_days = 30;
        } else if (($mon % 2 == 0) && ($mon >= 8)) {
            //even months after July(6)
            $tot_days = 31;
        } else {
            echo "outof range input";
        }


		if($qry0){ 		 	
            $res["success"] = 1;
            $res["message"] = "Attenance found Successfully.";
            $res["Attendance"] = array();

            while($row0 = mysqli_fetch_assoc($qry0)){
                $c0++;
                $eid = $row0['emp_id'];
            
                
                $qry=mysqli_query($con,"select count(emp_id) as tot_att from attendance_management where emp_id = '$eid'");                
                if($qry){                  				
                    while($row1 = mysqli_fetch_assoc($qry)){	                        				
                        $sal = array(); 
                        $sal["emp_id"] = $eid;
                        $sal["tot_att"] = $row1["tot_att"];
                        $sal["tot_days"] = $tot_days;
                        array_push($res["Attendance"], $sal);                          
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