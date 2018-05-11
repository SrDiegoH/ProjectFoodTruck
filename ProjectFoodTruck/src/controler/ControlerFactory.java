package controler;

public class ControlerFactory {
	private static ControlerFoodTruck foodTruckControler;
	public static ControlerFoodTruck getFoodTruckControler() {
		if (foodTruckControler == null) 
			foodTruckControler = new ControlerFoodTruck();
		return foodTruckControler;
	}
		
	private static ControlerPrato pratoControler;
	public static ControlerPrato getPratoControler() {
		if (pratoControler == null) 
			pratoControler = new ControlerPrato();
		return pratoControler;
	}
	
	private static ControlerLocal localControler;
	public static ControlerLocal getLocalControler() {
		if (localControler == null) 
			localControler = new ControlerLocal();
		return localControler;
	}
	
	private static ControlerSession sessionControler;
	public static ControlerSession getSessionControler() {
		if (sessionControler == null) 
			sessionControler = new ControlerSession();
		return sessionControler;
	}
}
