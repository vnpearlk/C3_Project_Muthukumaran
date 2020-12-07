import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void createRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //------r-FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        Restaurant spyRestaurant = Mockito.spy(restaurant);

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:30:00"));
        assertTrue(spyRestaurant.isRestaurantOpen());

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("13:00:00"));
        assertTrue(spyRestaurant.isRestaurantOpen());

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:00:00"));
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        Restaurant spyRestaurant = Mockito.spy(restaurant);

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:29:59"));
        assertFalse(spyRestaurant.isRestaurantOpen());

        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:00:01"));
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDERS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_1_or_more_existing_items_to_empty_or_existing_order_should_sum_up_order_total_based_on_items_ordered() throws itemNotFoundException {
        int curTotal = restaurant.addItemToOrder("Sweet corn soup");
        assertEquals(119, curTotal);
        assertEquals(119, restaurant.getOrderTotal());

        restaurant.addToMenu("Idli", 10);
        curTotal = restaurant.addItemToOrder("Idli");
        assertEquals(129, curTotal);
        assertEquals(129, restaurant.getOrderTotal());
    }

    @Test
    public void adding_non_existing_menu_item_to_existing_order_should_throw_exception_without_changing_total() throws itemNotFoundException {
        int curTotal = restaurant.addItemToOrder("Sweet corn soup");
        assertThrows(itemNotFoundException.class, () -> restaurant.addItemToOrder("Idli"));
        assertEquals(119, curTotal);
        assertEquals(119, restaurant.getOrderTotal());
    }

    @Test
    public void adding_non_existing_menu_item_to_empty_order_should_throw_exception_retaining_total_as_0() throws itemNotFoundException {
        assertThrows(itemNotFoundException.class, () -> restaurant.addItemToOrder("Idli"));
        assertEquals(0, restaurant.getOrderTotal());
    }

    @Test
    public void removing_1_or_more_existing_menu_items_from_existing_order_should_reduce_order_total_based_on_items_removed() throws itemNotFoundException {
        restaurant.addItemToOrder("Sweet corn soup");
        restaurant.addToMenu("Idli", 10);
        restaurant.addItemToOrder("Idli");

        int curTotal = restaurant.removeItemFromOrder("Idli");
        assertEquals(119, curTotal);
        assertEquals(119, restaurant.getOrderTotal());

        curTotal = restaurant.removeItemFromOrder("Sweet corn soup");
        assertEquals(0, curTotal);
        assertEquals(0, restaurant.getOrderTotal());
    }

    @Test
    public void removing_existing_menu_item_from_existing_order_that_doesnt_contain_this_item_should_throw_exception_without_changing_total() throws itemNotFoundException {
        restaurant.addItemToOrder("Sweet corn soup");
        assertThrows(itemNotFoundException.class, () -> restaurant.removeItemFromOrder("Vegetable lasagne"));
        assertEquals(119, restaurant.getOrderTotal());
    }

    @Test
    public void removing_non_existing_menu_item_from_existing_order_should_throw_exception_without_changing_total() throws itemNotFoundException {
        restaurant.addItemToOrder("Sweet corn soup");
        assertThrows(itemNotFoundException.class, () -> restaurant.removeItemFromOrder("Idli"));
        assertEquals(119, restaurant.getOrderTotal());
    }

    @Test
    public void removing_existing_menu_items_from_empty_order_should_retain_total_as_0() {
        assertThrows(itemNotFoundException.class, () -> restaurant.removeItemFromOrder("Sweet corn soup"));
        assertEquals(0, restaurant.getOrderTotal());
    }

    @Test
    public void removing_non_existing_menu_item_from_empty_order_should_throw_exception_retaining_total_as_0() {
        assertThrows(itemNotFoundException.class, () -> restaurant.removeItemFromOrder("Idli"));
        assertEquals(0, restaurant.getOrderTotal());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<ORDERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}