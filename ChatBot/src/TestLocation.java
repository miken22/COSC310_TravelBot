public class TestLocation {
    public static void main(String[] args) {
        Location loc = new Location("Tijuana, Mexico");

        System.out.println(loc.getPlaces("food"));
        System.out.println(loc.tempInCelcius);
    }
}