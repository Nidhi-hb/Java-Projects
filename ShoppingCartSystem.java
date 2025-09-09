import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingCartSystem {
    
    // Product class to represent items in the store
    static class Product {
        int id;
        String name;
        double price;
        
        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
    
    // CartItem class to represent items in the cart
    static class CartItem {
        Product product;
        int quantity;
        
        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        
        public double getTotalPrice() {
            return product.price * quantity;
        }
    }
    
    // ShoppingCart class to manage cart operations
    static class ShoppingCart {
        List<CartItem> items = new ArrayList<>();
        
        public void addItem(Product product, int quantity) {
            // Check if product already exists in cart
            for (CartItem item : items) {
                if (item.product.id == product.id) {
                    item.quantity += quantity;
                    return;
                }
            }
            // If not, add new item
            items.add(new CartItem(product, quantity));
        }
        
        public void removeItem(int productId) {
            items.removeIf(item -> item.product.id == productId);
        }
        
        public void updateQuantity(int productId, int newQuantity) {
            for (CartItem item : items) {
                if (item.product.id == productId) {
                    item.quantity = newQuantity;
                    return;
                }
            }
        }
        
        public void displayCart() {
            if (items.isEmpty()) {
                System.out.println("Your cart is empty.");
                return;
            }
            
            System.out.println("\nYour Shopping Cart:");
            System.out.println("ID\tName\t\tPrice\tQty\tTotal");
            System.out.println("--------------------------------------------");
            
            for (CartItem item : items) {
                System.out.printf("%d\t%s\t$%.2f\t%d\t$%.2f%n",
                        item.product.id,
                        item.product.name,
                        item.product.price,
                        item.quantity,
                        item.getTotalPrice());
            }
            
            System.out.println("--------------------------------------------");
            System.out.printf("Total: $%.2f%n", calculateTotal());
        }
        
        public double calculateTotal() {
            double total = 0;
            for (CartItem item : items) {
                total += item.getTotalPrice();
            }
            return total;
        }
        
        public void clearCart() {
            items.clear();
        }
    }
    
    public static void main(String[] args) {
        // Initialize some sample products
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 999.99));
        products.add(new Product(2, "Smartphone", 699.99));
        products.add(new Product(3, "Headphones", 149.99));
        products.add(new Product(4, "Mouse", 29.99));
        products.add(new Product(5, "Keyboard", 59.99));
        
        ShoppingCart cart = new ShoppingCart();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the E-Commerce Shopping System!");
        
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Products");
            System.out.println("2. View Cart");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Update Product Quantity");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
            System.out.print("Please choose an option: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the buffer
                continue;
            }
            
            switch (choice) {
                case 1: // View Products
                    System.out.println("\nAvailable Products:");
                    System.out.println("ID\tName\t\tPrice");
                    System.out.println("------------------------");
                    for (Product product : products) {
                        System.out.printf("%d\t%s\t$%.2f%n", 
                                product.id, product.name, product.price);
                    }
                    break;
                    
                case 2: // View Cart
                    cart.displayCart();
                    break;
                    
                case 3: // Add to Cart
                    System.out.print("Enter product ID to add: ");
                    int addId = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int addQty = scanner.nextInt();
                    
                    Product addProduct = null;
                    for (Product p : products) {
                        if (p.id == addId) {
                            addProduct = p;
                            break;
                        }
                    }
                    
                    if (addProduct != null) {
                        cart.addItem(addProduct, addQty);
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                    
                case 4: // Remove from Cart
                    System.out.print("Enter product ID to remove: ");
                    int removeId = scanner.nextInt();
                    cart.removeItem(removeId);
                    System.out.println("Product removed from cart.");
                    break;
                    
                case 5: // Update Quantity
                    System.out.print("Enter product ID to update: ");
                    int updateId = scanner.nextInt();
                    System.out.print("Enter new quantity: ");
                    int newQty = scanner.nextInt();
                    cart.updateQuantity(updateId, newQty);
                    System.out.println("Quantity updated.");
                    break;
                    
                case 6: // Checkout
                    if (cart.items.isEmpty()) {
                        System.out.println("Your cart is empty. Nothing to checkout.");
                    } else {
                        cart.displayCart();
                        System.out.println("\nProceeding to checkout...");
                        System.out.println("Thank you for your purchase!");
                        cart.clearCart();
                    }
                    break;
                    
                case 7: // Exit
                    System.out.println("Thank you for shopping with us!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
