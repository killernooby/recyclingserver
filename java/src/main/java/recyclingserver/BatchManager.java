package recyclingserver;

import com.datastax.driver.core.exceptions.SyntaxError;

public class BatchManager {
	
	final String base =   "INSERT INTO recycling.recycledMaterials (userId, locationID, time, paper, plastic, glas, metal) VALUES" ;
	
	int maxBatchSize;
	int timeout;
	String batch;
	int batchSize;
	DBConnection dbcon;
	
	public BatchManager(String node,int maxBatchSize, int timeout){
		this.maxBatchSize = maxBatchSize;
		this.timeout = timeout;
		dbcon = new DBConnection();
		dbcon.connect(node);
	}
	
	
	private String recoverQuery(String batch){
		//batch.replaceAll(base + "\([\w-]+, [\w-]+, [], \)\n", "")
		
		
		return null;
	}
	
	public void forceCommit(){
		batch = batch + "APPLY BATCH;";
		try{
			dbcon.query(batch);
		}catch(SyntaxError E){
			System.out.println("Error: Malformed query in batch.");
			System.out.println(batch);
			//TODO: recover error.
			E.printStackTrace();
		}
		
		batch = String.format("BEGIN BATCH%n");
		batchSize = 0;
	}
	
	public void addTransaction(String transaction){
		if(batchSize == 0){
			batch = String.format("BEGIN BATCH%n");
		}else if(batchSize == maxBatchSize){
			forceCommit();
		}
		
		batch += base + String.format("(%s)%n", transaction);
		batchSize++;		
	}
}
