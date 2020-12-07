import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(restaurantName))
                return restaurant;
        }

        // Method is expected to throw exception if no restaurants are found instead of returning null
        // Ref discussion at https://learn.upgrad.com/v/course/1003/question/313751
        throw new restaurantNotFoundException(restaurantName);

    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        // Refactored this method as per changed impl of findRestaurantByName(),
        // refer findRestaurantByName() implementation for details.
        try {
            Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
            restaurants.remove(restaurantToBeRemoved);
            return restaurantToBeRemoved;
        } catch (restaurantNotFoundException re) {
            throw new restaurantNotFoundException(restaurantName);
        }

    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
