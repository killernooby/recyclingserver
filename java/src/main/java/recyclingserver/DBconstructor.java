package recyclingserver;

import java.util.ArrayList;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class DBconstructor {
	private Cluster cluster;
	private Session session;

	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect();
	}

	public void close() {
		cluster.close();
	}

	public void createSchema() {
		session.execute("CREATE KEYSPACE recycling WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};");
		//User table
		session.execute("CREATE TABLE recycling.users (" 
				+ "userID uuid PRIMARY KEY,"
				+ "firstname text," 
				+ "lastname text," 
				+ "IDnumber int,"
				+ "pin int,"
				+ "email text,"  
				+ "username text," 
				+ "password text,"
				+ "usertype text"+ ");");
		
		session.execute("CREATE TABLE recycling.location (" 
				+ "locationID uuid PRIMARY KEY,"
				+ "locationName text," 
				+ "locationAddress text, " 
				+ "longitude double,"
				+ "lattitude double," 
				+ "ip inet"
				+ ");");
		
		session.execute("CREATE TABLE recycling.recycledMaterials ("
				+ "userID uuid,"
				+ "locationID uuid,"
				+ "time timestamp,"
				+ "paper int,"
				+ "plastic int,"
				+ "glas int,"
				+ "metal int,"
				+ "PRIMARY KEY (time, userID, locationID)"
				+ ");");
		
		//CREATE TABLE recycling.recycledMaterials (userID uuid,locationID uuid,time timestamp,paper int,plastic int,glas int,metal int,PRIMARY KEY ( userID, time,locationID));
			
	}
	
	public void dropTable(){
		session.execute("DROP TABLE recycling.users");
		session.execute("DROP TABLE recycling.location");
		session.execute("DROP TABLE recycling.recycledMaterials");
	}
	
	public void addSampleData(){
		String batch = String.format("BEGIN BATCH%n");
		
		/*
		 * username | userid -----------+--------------------------------------
		 * ckramer | 39659b52-97e4-4513-b3b9-4061974c9bd5 
		 * jseinfeld | a30f1f78-ce11-4f52-963c-40ff1903cb10 
		 * ebenes | fe9140a4-a18d-47ad-8fb6-8c85d3a5f7d8 
		 * nycadmin | 85f81b76-30c5-415a-b03d-77a3be842705 
		 * gcostanza | 6ab62cc1-d858-48f6-b52f-194203bdade3
		 */

		/*
		 * locationname | locationid
		 * --------------------+-------------------------------------- 
		 * Jet Propulsion Lab | 3cb24832-f1b7-4b1c-a191-e644c8574142 
		 * Kwik-E-Mart | 484f7753-adbe-45ae-b1d9-492c6c5a1c07 
		 * Area 51 | a3ca2c55-58a6-4edc-b242-e3d353ea74af
		 */
				
		String base =   "INSERT INTO recycling.users (userID, firstname, lastname, IDnumber, pin, email, username, password, usertype) VALUES" ;
		UUID juuid = UUID.fromString("a30f1f78-ce11-4f52-963c-40ff1903cb10");
		UUID guuid = UUID.fromString("6ab62cc1-d858-48f6-b52f-194203bdade3");
		UUID euuid = UUID.fromString("fe9140a4-a18d-47ad-8fb6-8c85d3a5f7d8");
		UUID cuuid = UUID.fromString("39659b52-97e4-4513-b3b9-4061974c9bd5");
		batch = batch + base + String.format("(%s,'Jerry', 'Seinfeld', 1, 1234, 'seinfeld@madeup.com','jseinfeld','notmypassword','normal')%n",juuid);
		batch = batch + base + String.format("(%s,'George', 'Costanza', 2, 4321, 'costanza@madeup.com','gcostanza','notmypassword','normal')%n",guuid);
		batch = batch + base + String.format("(%s,'Elaine', 'Benes', 3, 5678, 'benes@madeup.com','ebenes','notmypassword','normal')%n",euuid);
		batch = batch + base + String.format("(%s,'Cosmo', 'Kramer', 4, 8765, 'kramer@kramer.com','ckramer','notmypassword','normal')%n",cuuid);
		batch = batch + base + String.format("(%s,'New York','City', 5, 2468, 'tech@nyc.com','nycadmin','notmypassword','Municipality')%n",UUID.fromString("85f81b76-30c5-415a-b03d-77a3be842705"));
		
		UUID xuuid = UUID.fromString("484f7753-adbe-45ae-b1d9-492c6c5a1c07");
		UUID yuuid = UUID.fromString("3cb24832-f1b7-4b1c-a191-e644c8574142");
		UUID zuuid = UUID.fromString("a3ca2c55-58a6-4edc-b242-e3d353ea74af");
		base =   "INSERT INTO recycling.location (locationID, locationName, locationAddress, longitude, lattitude, ip) VALUES" ;
		
		batch = batch + base + String.format("(%s,'Kwik-E-Mart', 'Springfield', -93.304221, 37.201258, '127.0.0.1')%n",xuuid);
		batch = batch + base + String.format("(%s,'Jet Propulsion Lab', '4800 Oak Grove', -118.171383, 34.201095, '192.168.1.1')%n",yuuid);
		batch = batch + base + String.format("(%s,'Area 51', 'Lincoln County, Nevada', -115.810018, 37.236402, '8.8.8.8')%n",zuuid);
		
		base =   "INSERT INTO recycling.recycledMaterials (userId, locationID, time, paper, plastic, glas, metal) VALUES" ;
		batch = batch + base + String.format("(%s,%s, dateof(now()), 100, 0, 0, 0)%n",juuid,xuuid);
		batch = batch + base + String.format("(%s,%s, dateof(now()), 0, 42, 999, 333)%n",cuuid,zuuid);
		batch = batch + base + String.format("(%s,%s, dateof(now()), 1, 23, 0, 345)%n",guuid,yuuid);
		batch = batch + base + String.format("(%s,%s, dateof(now()), 7, 11, 13, 17)%n",euuid,xuuid);
		batch = batch + base + String.format("(%s,%s, dateof(now()), 11, 15, 235, 234)%n",juuid,zuuid);
		batch = batch + base + String.format("(%s,%s, dateof(now()), 12, 34, 56, 78)%n",euuid,xuuid);
		
		
		batch = batch + "APPLY BATCH;";
		System.out.print(batch);
		session.execute(batch);
	}
	
	public static void main(String[] args) {
		  DBconstructor client = new DBconstructor();
		  client.connect("127.0.0.1");
		  //client.dropTable();
		  client.createSchema();
		  client.addSampleData();
		  client.close();
		}
}
