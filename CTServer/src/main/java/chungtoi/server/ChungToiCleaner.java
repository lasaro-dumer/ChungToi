package chungtoi.server;

public class ChungToiCleaner implements Runnable {
	private ChungToi chungToi;
	private String lastStatus;

	public ChungToiCleaner(ChungToi chungToi){
		this.chungToi = chungToi;
		this.lastStatus = "";
	}

	@Override
	public void run() {
		try {
			int removedPlayers = chungToi.checkPlayersTimeouts();
			if (removedPlayers > 0) {
				chungToi.log(String.format("Removed %s players", removedPlayers));
			}
			String status = chungToi.printStatus();
			if(!status.equals(this.lastStatus)){
				chungToi.log(status);
				this.lastStatus = status;
			}
		} catch (Exception e) {
			chungToi.log(e);
		}
	}
}
