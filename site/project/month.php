<?php
	function validateDate($date, $format = 'd-m-Y')
				{
				    $d = DateTime::createFromFormat($format, $date);
				    return $d && $d->format($format) == $date;
				}
				$fromval = '';
				$toval = '';
					
					if (isset($_POST['sorting'])) {
						if(validateDate($_POST['fromdate'])){
							$fromdate = date_create_from_format('d-m-Y', $_POST['fromdate']);
							echo  "Found data: " . $fromdate->format("Y-m-d") . "<br>";
							$fromval = "value=\"" . $fromdate->format("d-m-Y") . "\"";
						}
						if (validateDate($_POST['todate'])) {
							$todate = date_create_from_format('d-m-Y', $_POST['todate']);
							echo  "Found data: " . $todate->format("Y-m-d"). "<br>";
							$toval = "value=\"" . $todate->format("d-m-Y") . "\"";
						}

						echo "something";
					}else{
						echo "nothing";
						$_POST['sorting'] = "month";
					}
					var_dump($_POST);

					function timefilter(){
						global $fromdate;
						global $todate;
						$retstr = '';

						//var_dump($fromdate);
						//var_dump($todate);
						if(isset($fromdate)){
							$retstr .= 'WHERE time >= \'' . $fromdate->format("Y-m-d") . '\''; 
						}
						if(isset($todate)){
							if($retstr == ''){
								$retstr = 'WHERE ';
							}else{
								$retstr .= ' AND ';
							}
							$retstr .= " time < '" . $todate->format("Y-m-d") . '\'';
						}
						var_dump($retstr);
						return $retstr;
					}
			?>
				<div class="panel-group" id="accordion">
  					<div class="panel panel-default">
  					  <div class="panel-heading">
  					    <h4 class="panel-title">
  					      <a data-toggle="collapse" data-parent="#accordion" href="#timeframe">
  					        Timeframe
  					      </a>
  					    </h4>
  					  </div>
  					  <div id="timeframe" class="panel-collapse collapse">
  					    <div class="panel-body">
  					      <FORM action="" method="post">
 						   <P>
 						   <LABEL for="fromdate">From: </LABEL>
 						   <input data-format="dd-MM-yyyy" type="text" id="fromdate" name="fromdate" <?php echo $fromval ?> placeholder="dd-MM-yyyy"></input>
 						   <LABEL for="todate">To: </LABEL>
 						   <input data-format="dd-MM-yyyy" type="text" id="todate" name="todate" <?php echo $toval ?> placeholder="dd-MM-yyyy"></input><br>
 							<label class="checkbox-inline">
    							<input type="radio" name="sorting" id="optionDay" 
    						     value="day" <?php if($_POST['sorting'] == "day"){echo "checked";} ?>> Daily
    						</label>
    						<label class="checkbox-inline">
    						  	<input type="radio" name="sorting" id="optionWeek" 
    						     value="week" <?php if($_POST['sorting'] == "week"){echo "checked";} ?>> Weekly
    						</label>
    						<label class="checkbox-inline">
    						  	<input type="radio" name="sorting" id="optionMonth" 
    						     value="month" <?php if($_POST['sorting'] == "month"){echo "checked";} ?>> Monthly
    						</label>
    						<label class="checkbox-inline">
    						  	<input type="radio" name="sorting" id="optionYear" 
    						     value="year" <?php if($_POST['sorting'] == "year"){echo "checked";} ?>>  Yearly
    						</label>
 						   <INPUT type="submit" value="Send"> <INPUT type="reset">
 						   </P>
 						</FORM>
  					    </div>
  					  </div>
  					</div>
  				</div>