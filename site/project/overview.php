
<!DOCTYPE html>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="shortcut icon" href="assets/ico/favicon.ico">

		<title>Recycling</title>
		<script src="lib/ChartNew.js-master/ChartNew.js"></script>
		<meta name = "viewport" content = "initial-scale = 1, user-scalable = no">
		<style>
			canvas{
			}
		</style>

		<!-- Bootstrap core CSS -->
		<link href="dist/css/bootstrap.min.css" rel="stylesheet">

		<!-- Custom styles for this template -->
		<link href="starter-template.css" rel="stylesheet">

		<!-- Just for debugging purposes. Don't actually copy this line! -->
		<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>

		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Recycling</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li><a href="index.php">Home</a></li>
						<li class="active"><a href="#">Overview</a></li>
						
					</ul>
				</div><!--/.nav-collapse -->
			</div>
		</div>

		<div class="container">

			<div class="starter-template">

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
							//echo  "Found data: " . $fromdate->format("Y-m-d") . "<br>";
							$fromval = "value=\"" . $fromdate->format("d-m-Y") . "\"";
						}
						if (validateDate($_POST['todate'])) {
							$todate = date_create_from_format('d-m-Y', $_POST['todate']);
							//echo  "Found data: " . $todate->format("Y-m-d"). "<br>";
							$toval = "value=\"" . $todate->format("d-m-Y") . "\"";
						}
					}
					var_dump($_POST);
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
 						   <INPUT type="submit" value="Send"> <INPUT type="reset">
 						   </P>
 						</FORM>
  					    </div>
  					  </div>
  					</div>
  				</div>
  				
				 <?php
					
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
					$db_handle = new PDO("cassandra:host=localhost;port=9160", www_cassandra, yourpassword);
					//echo "SELECT * FROM recycling.recycledMaterials " . timefilter() . " ALLOW FILTERING";
					$stmt = $db_handle->prepare("SELECT * FROM recycling.recycledMaterials " . timefilter() . " ALLOW FILTERING");
					$stmt->execute();

					$result = $stmt->fetchAll(PDO::FETCH_ASSOC);
					//print_r($result);
					//echo '<br>';

					//Cassandra has no joins so we make our own.
					include 'util.php';
					?>
					<div class="table-responsive">
					<table class="table table-bordered table-striped">
					<thead>
						<tr>
						  <th>
						  	Name
						  </th>
						  <th>
							Location
						  </th>
						  <th>
							Time
						  </th>
						  <th>
							Glass
						  </th>
						  <th>
							Metal
						  </th>
						  <th>
							Paper
						  </th>
						  <th>
							Plastic
						  </th>
						</tr>
					</thead>
					<tbody>
					<?php

					$sumarr['glas'] = 0;
					$sumarr['metal'] = 0;
					$sumarr['paper'] = 0;
					$sumarr['plastic'] = 0;
					
					foreach ($result as $i) {

						echo '<tr><th>' . getPersonName($i['userid']) . '</th>';
						echo '<th>' . getLocationName($i['locationid']) . '</th>';
						echo '<th>' . getDateStringFromHex($i['time']) . '</th>';
						echo '<th>' . $i['glas'] . '</th>';
						echo '<th>' . $i['metal'] . '</th>';
						echo '<th>' . $i['paper'] . '</th>';
						echo '<th>' . $i['plastic'] . '</th></tr>';

						$sumarr['glas'] += $i['glas'];
						$sumarr['metal'] += $i['metal'];
						$sumarr['paper'] += $i['paper'];
						$sumarr['plastic'] += $i['plastic'];
					}
					
					echo '<tr><th>Total ('. sizeof($result) .' items)</th>';
					echo '<th></th>';
					echo '<th></th>';
					echo '<th>' . $sumarr['glas'] . '</th>';
					echo '<th>' . $sumarr['metal'] . '</th>';
					echo '<th>' . $sumarr['paper'] . '</th>';
					echo '<th>' . $sumarr['plastic'] . '</th></tr>';


					echo '</tbody></table></div>';

				?>


			</div>

		</div><!-- /.container -->


		<!-- Bootstrap core JavaScript
		================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="dist/js/bootstrap.min.js"></script>
	</body>
</html>