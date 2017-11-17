package chungtoi.server;

public class ChungToiCleaner implements Runnable {
	private ChungToi chungToi;

	public ChungToiCleaner(ChungToi chungToi){
		this.chungToi = chungToi;
	}

	@Override
	public void run() {
		try {
			int removedPlayers = chungToi.checkPlayersTimeouts();
			if (removedPlayers > 0) {
				chungToi.log(String.format("Removed %s players", removedPlayers));
			}
			chungToi.log(chungToi.printStatus());
		} catch (Exception e) {
			chungToi.log(e);
		}
	}
}
