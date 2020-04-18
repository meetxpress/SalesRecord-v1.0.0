<?php
    $res = array();
    if(isset($_POST['for_id']) && $_POST['for_refNo']){
        //Company
        //?for_id=100041&for_refNo=12121212

        //employee
        //?for_id=300001&for_refNo=123456789012

		$for_id=$_POST['for_id'];
        $for_refNo=$_POST['for_refNo'];
        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        
		if(substr($for_id, 0, 1) == '1'){
            $qry = mysqli_query($con,"select * from company_master where comp_id='$for_id' and comp_status='Active'");
			if(mysqli_num_rows($qry) > 0){			
                $row = mysqli_fetch_assoc($qry);
                //echo "<h1> Company: ".$row['comp_licno']."</h1>";
                
                if($for_refNo == $row['comp_licno']){
                    $res["success"] = 1;
                    $res["message"] = "User(Company) Found.";
            
                    echo json_encode($res);
                } else {
                    $res["success"] = 0;					
				    $res["message"] = "User(Company) Not Found!";
			
				    echo json_encode($res);
                }
                
			} else{
				$res["success"] = 0;					
				$res["message"] = "Something went wrong..";
			
				echo json_encode($res);
			}	
        } else if(substr($for_id, 0, 1) == '3'){
            $qry = mysqli_query($con,"select * from employee_master where emp_id='$for_id' and emp_status='Active'");
			if(mysqli_num_rows($qry) > 0){	
                $row = mysqli_fetch_assoc($qry);
                //echo "<h1> Employee: ".$row['emp_aadharno']."</h1>";
				
                if($for_refNo == $row['emp_aadharno']){
                    $res["success"] = 2;
                    $res["message"] = "User(Employee) Found.";
            
                    echo json_encode($res);
                } else {
                    $res["success"] = 0;					                    
				    $res["message"] = "Something went wrong..";
                    
				    echo json_encode($res);
                }                
			} else{
				$res["success"] = 0;					
				$res["message"] = "Data not found.";
			
				echo json_encode($res);
			}	
        }        
    }else{
        $res["success"] = 0;
        $res["message"] = "Required Field(s) is(are) missing.";

        echo json_encode($res);
    }
?>