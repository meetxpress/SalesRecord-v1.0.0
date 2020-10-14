<?php
    $c0=0;
    $res = array();
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry0=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");
        if(mysqli_num_rows($qry0) > 0 ){ 
            while($row0 = mysqli_fetch_assoc($qry0)){
                $c0++;
                $eid = $row0['emp_id'];

                $qry=mysqli_query($con,"select atarget,atargetmonth from emp_target where comp_id = '".$_POST['comp_id']."'");                
                $qry2=mysqli_query($con,"select SUM(rt_unit) as totSell,emp_id from emp_routinetask where emp_id = '$eid' ");
            
                if((mysqli_num_rows($qry) > 0 ) and (mysqli_num_rows($qry2) > 0 )){ 
                    //creating json array to store json in array format
                    $res["Target"] = array();
        
                    while(($row = mysqli_fetch_assoc($qry)) and ($row2 = mysqli_fetch_assoc($qry2)) ){
                        $mon = $row['atargetmonth'];
        
                        $tar = array();
                        $tar["mon"] = $mon;
                        $tar["atarget"] = $row["atarget"];
                        $tar["p_target"] = $row2["totSell"];
        
                        if($_POST['yr'] == $mon){                    
                            $res["success"] = 1;
                            $res["message"] = "Target found Successfully.";
                            
                            array_push($res["Target"], $tar);
                        }
                    }
                    echo json_encode($res);			
                }else{
                    $res["success"] = 0;					
                    $res["message"] = "No data found";
                             
                    echo json_encode($res);
                }	
            }
        }        
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>