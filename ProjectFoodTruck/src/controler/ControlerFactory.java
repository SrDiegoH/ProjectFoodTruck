package controler;

//import java.util.HashMap;

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
	
	
//	public static final String FOODTRUCK = "foodtruck";
//	public static final String PRATO = "prato";
//	
//	private final HashMap<String, ControlerBase> CONTROLERS;
//	{
//		CONTROLERS = new HashMap<>();
//		CONTROLERS.put(FOODTRUCK, new ControlerFoodTruck());
//		CONTROLERS.put(PRATO, new ControlerPrato());
//		//so sao instanciados uma vez
//	}
//	
//	public ControlerBase getControlers(String chave) {
//		return CONTROLERS.get(chave);
//	}
}
