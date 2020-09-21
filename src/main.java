package courseProject;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws IOException {
		//Print welcome message
		System.out.print("Welcome to A+ Music Store!\n\n");

		//Read orders database file
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		File file_orders = new File("orders.txt");
		Heap orderHeap = new Heap();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file_orders));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		try {
			String[] temp = new String[9];
			while ((temp[0] = br.readLine()) != null) {
				for (int i = 1; i < 9; i++) {
					temp[i] = br.readLine();
				}

				Order order = new Order(Integer.parseInt(temp[0]),
						LocalDate.parse(temp[1], formatter),
						Integer.parseInt(temp[2]),
						Integer.parseInt(temp[3]),
						Order.Shipping.ofCode(Integer.parseInt(temp[4])),
						temp[5], temp[6], temp[7]);
				orderHeap.insert(order);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		br.close();
		int orderId = orderHeap.getSize();

		//Read customers database file
		File file_customers = new File("customers.txt");

		br = null;
		try {
			br = new BufferedReader(new FileReader(file_customers));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Hash customerHash = new Hash(32);
		ArrayList<Customer> customerList = new ArrayList<>();

		try {
			String[] temp = new String[11];
			while((temp[0] = br.readLine()) != null) {
				List<Order> orderIds = new List<>();

				for (int i = 1; i < 11; i++) {
					temp[i] = br.readLine();
				}
				String[] orders = temp[9].split(",");
				for(int k = 0; k < orders.length; k++){
					orderIds.addLast(orderHeap.searchOrder(Integer.parseInt(orders[k])));
				}
				Customer customer = new Customer(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6],
						Integer.parseInt(temp[7]), temp[8], orderIds);
				customerHash.insert(customer);
				customerList.add(customer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		br.close();
		//Read products database file
		File file_products = new File("products.txt");
		ArrayList<Product> productStores = new ArrayList<>();

		br = null;
		try {
			br = new BufferedReader(new FileReader(file_products));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BST<Product> primaryBST = new BST<Product>();
		BST<Product> secondaryBST = new BST<Product>();

		try {
			String[] temp = new String[9];
			while((temp[0] = br.readLine()) != null) {
				List<String> SongLists = new List<>();
				for(int i = 1; i < 9; i++) {
					temp[i] = br.readLine();
				}
				String[] SList = temp[5].split(",");
				for(int k = 0; k < SList.length; k++){
					SongLists.addLast(SList[k]);
				}
				Product product1 = new Product(temp[1], temp[1], temp[2], Double.valueOf(temp[3]),
						Integer.parseInt(temp[4]), SongLists, LocalDate.parse(temp[6], formatter), temp[7]);
				primaryBST.insert(product1);
				productStores.add(product1);
				Product product2 = new Product(temp[2], temp[1], temp[2], Double.valueOf(temp[3]),
						Integer.parseInt(temp[4]), SongLists, LocalDate.parse(temp[6], formatter), temp[7]);
				secondaryBST.insert(product2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		br.close();

		//Identify the user
		boolean cont = true;
		boolean loggedInCustomer = false;
		boolean loggedInEmployee = false;
		String username = "";
		String password = "";
		String option = "";
		Customer thisCustomer = new Customer();
		Scanner scan = new Scanner(System.in);
		while(cont) {
			System.out.print("Are you a customer or an employee?\n\n" +
					"1. Customer\n" +
					"2. Employee\n" +
					"Enter your choice: ");
			option = scan.nextLine();
			System.out.print("\n");

			//Log in as a customer
			if(option.equals("1")) {
				//cont = true;
				while(cont) {
					System.out.print("Please select from the following options:\n\n" +
							"A. Enter username and password\n" +
							"B. Create a new account\n" +
							"C. Log in as a guest\n" +
							"Enter your choice: ");
					String input = scan.nextLine();
					System.out.print("\n");

					//Enter username and password
					if(input.equalsIgnoreCase("A")) {
						while(loggedInCustomer == false) {
							System.out.print("Please enter your username: ");
							username = scan.nextLine();
							System.out.print("Please enter your password: ");
							password = scan.nextLine();

							for(int i = 0; i < customerList.size(); i++) {
								Customer customer = customerList.get(i);
								if(customer.getUserName().equals(username) && customer.getPassWord().equals(password)) {
									loggedInCustomer = true;
									thisCustomer = customerList.get(i);
								}
							}

							if(loggedInCustomer == true) {
								System.out.print("\nYou are logged in as a member!\n\n");
								cont = false;
							} else {
								System.out.print("\nUsername and password are not matched!\n"
										+ "Please try to log in again!\n\n");
							}
						}
					}
					//Create a new account
					else if(input.equalsIgnoreCase("B")) {
						createAccount(customerHash, customerList);
						System.out.print("\nYou have created a new account!\n\n");

					}
					//Log in as a guest
					else if(input.equalsIgnoreCase("C")) {
						System.out.print("You are logged in as a guest!\n\n");
						cont = false;
					}else {
						System.out.print("Invalid input!\n");
					}
				}
			}

			//Log in as an employee
			else if(option.equals("2")) {
				//Enter username and password
				while(loggedInEmployee == false) {
					loggedInEmployee = idEmployee();
					if(loggedInEmployee == true) {
						System.out.print("You are logged in as an employee!\n\n");
						cont = false;
					} else {
						System.out.print("Username and password are not matched!\n"
								+ "Please try to log in again!\n\n");
					}
				}
			}else {
				System.out.print("Invalid input!\n");
			}
		}

		//Store menu
		cont = true;
		while(cont) {
			//If you are a customer
			if(option.equals("1")) {
				while(cont) {
					System.out.print("Please select from the following options:\n\n" +
							"A. Search for a product\n" +
							"B. Show all products\n" +
							"C. View purchases\n" +
							"X. Quit\n" +
							"Enter your choice: ");
					String input = scan.nextLine();
					System.out.print("\n");

					//Search for a product
					if(input.equalsIgnoreCase("A")) {
						System.out.print("Please select from the following options:\n\n" +
								"A. Search by title\n" +
								"B. Search by artist\n" +
								"Enter your choice: ");
						input = scan.nextLine();
						//Find it by primary key (title)
						if(input.equalsIgnoreCase("A")) {
							System.out.print("\nPlease search by entering the title: ");
							String key = scan.nextLine();
							if(searchProduct(primaryBST, key)) {
								ArrayList<Product> productList = new ArrayList<>();
								Product thisProduct = new Product(key, "", "", 0.0, 0, new List<String>(),
										LocalDate.now(), "");
								for(int i = 0; i < productList.size(); i++) {
									if(productList.get(i).getKey().equals(key)) {
										thisProduct = productList.get(i);
									}
								}
								System.out.print("\n" + key + " is available in our store!\n");
								System.out.print("Would you like to order this item (Y/N)? ");
								String s = scan.nextLine();

								if(loggedInCustomer && s.equalsIgnoreCase("Y")) {
									System.out.print("\nPlease select from the following options:\n\n" +
											"A. Overnight\n" +
											"B. Rush\n" +
											"C. Standard\n" +
											"Enter your choice: ");
									input = scan.nextLine();
									System.out.print("How many of this item would you like? ");
									int quantity = 0;
									quantity = scan.nextInt();
									scan.nextLine();
									System.out.print("\n");
									//orderId++;
									int priority = 0;
									//Overnight
									if(input.equalsIgnoreCase("A")) {
										priority = 3;
									}
									//Rush
									else if(input.equalsIgnoreCase("B")) {
										priority = 2;
									}
									//Standard
									else if(input.equalsIgnoreCase("C")) {
										priority = 1;
									}
									Order thisOrder = new Order(++orderId, LocalDate.now(), 0, quantity,
											Order.Shipping.ofCode(priority), key, thisCustomer.getUserName(), "");
									orderHeap.insert(thisOrder);
									thisCustomer.getOrders().addLast(thisOrder);
									System.out.print("Your order has been placed. Thank you!\n\n");
								}
								else if(!loggedInCustomer && s.equalsIgnoreCase("Y")) {
									System.out.print("Please create a new account before placing an order.\n");
								}
							}
							else {
								System.out.print("The product you're looking for is not found in our store.\n");
							}
						}
						//Find it by secondary key (artist)
						else if(input.equalsIgnoreCase("B")) {
							System.out.print("\nPlease search by entering the artist name: ");
							String key = scan.nextLine();
							if(searchProduct(secondaryBST, key)) {
								ArrayList<Product> productList = new ArrayList<>();
								Product thisProduct = new Product(key, "", "", 0.0, 0, new List<String>(),
										LocalDate.now(), "");
								for(int i = 0; i < productList.size(); i++) {
									if(productList.get(i).getKey().equals(key)) {
										thisProduct = productList.get(i);
									}
								}
								System.out.printf("\n We have one album from " + key + " available in our store! Album name: %s\n", thisProduct.getTitle());
								System.out.print("Would you like to order this item (Y/N)? ");
								String s = scan.nextLine();

								if(loggedInCustomer && s.equalsIgnoreCase("Y")) {
									System.out.print("\nPlease select from the following options:\n\n" +
											"A. Overnight\n" +
											"B. Rush\n" +
											"C. Standard\n" +
											"Enter your choice: ");
									input = scan.nextLine();
									System.out.print("How many of this item would you like? ");
									int quantity = 0;
									quantity = scan.nextInt();
									scan.nextLine();
									System.out.print("\n");
									orderId++;
									int priority = 0;
									//Overnight
									if(input.equalsIgnoreCase("A")) {
										priority = 3;
									}
									//Rush
									else if(input.equalsIgnoreCase("B")) {
										priority = 2;
									}
									//Standard
									else if(input.equalsIgnoreCase("C")) {
										priority = 1;
									}
									Order thisOrder = new Order(orderId, LocalDate.now(), 0, quantity,
											Order.Shipping.ofCode(priority), thisProduct.getTitle(), thisCustomer.getUserName(), "");
									orderHeap.insert(thisOrder);
									thisCustomer.getOrders().addLast(thisOrder);
									System.out.print("Your order has been placed. Thank you!\n\n");
								}
								else if(!loggedInCustomer && s.equalsIgnoreCase("Y")) {
									System.out.print("Please create a new account before placing an order.\n");
								}
							}
							else {
								System.out.print("The product you're looking for is not found in our store.\n");
							}
						}
					}
					//Show all products
					else if(input.equalsIgnoreCase("B")) {
						System.out.print("Please select from the following options:\n\n" +
								"A. Show by title\n" +
								"B. Show by artist\n" +
								"Enter your choice: ");
						input = scan.nextLine();
						//Sorted by primary key (title)
						if(input.equalsIgnoreCase("A")) {
							primaryBST.inOrderPrint();
						}
						//Sorted by secondary key (artist)
						else if(input.equalsIgnoreCase("B")) {
							secondaryBST.inOrderPrint();
						}
					}
					//View purchases
					else if(input.equalsIgnoreCase("C")) {
						if(loggedInCustomer) {
							System.out.print("Please select from the following options:\n\n" +
									"A. View shipped orders\n" +
									"B. View unshipped orders\n" +
									"Enter your choice: ");
							input = scan.nextLine();
							System.out.print("\n");
							//View shipped orders
							if(input.equalsIgnoreCase("A")) {
								boolean noShipped = true;
								List orderList = thisCustomer.getOrders();
								if(orderList.getLength() != 0) {
									orderList.pointIterator();
									Order currentOrder;
									for(int i = 0; i < orderList.getLength(); i++) {
										currentOrder = (Order) orderList.getIterator();
										if(currentOrder.getStatus() == 1) {
											noShipped = false;
										}
										orderList.advanceIterator();
									}
									if(noShipped) {
										System.out.print("You have no shipped orders for now!\n");
									} else {
										orderList.pointIterator();
										for(int i = 0; i < orderList.getLength(); i++) {
											currentOrder = (Order) orderList.getIterator();
											if(currentOrder.getStatus() == 1) {
												System.out.print(currentOrder.toString());
												System.out.print("\n");
											}
											orderList.advanceIterator();
										}
									}
								} else {
									System.out.print("You have no shipped orders for now!\n");
								}
								System.out.print("\n");
							}
							//View unshipped orders
							else if(input.equalsIgnoreCase("B")) {
								boolean noUnshipped = true;
								List orderList = thisCustomer.getOrders();
								if(orderList.getLength() != 0) {
									orderList.pointIterator();
									Order currentOrder;
									for(int i = 0; i < orderList.getLength(); i++) {
										currentOrder = (Order) orderList.getIterator();
										if(currentOrder.getStatus() == 0) {
											noUnshipped = false;
										}
										orderList.advanceIterator();
									}
									if(noUnshipped) {
										System.out.print("You have no unshipped orders for now!\n");
									} else {
										orderList.pointIterator();
										for(int i = 0; i < orderList.getLength(); i++) {
											currentOrder = (Order) orderList.getIterator();
											if(currentOrder.getStatus() == 0) {
												System.out.print(currentOrder.toString());
												System.out.print("\n");
											}
											orderList.advanceIterator();
										}
									}
								} else {
									System.out.print("You have no unshipped orders for now!\n");
								}
								System.out.print("\n");
							}
						} else {
							System.out.print("You need an account before viewing purchases.\n\n");
						}
					}
					//Quit
					else if(input.equalsIgnoreCase("X")) {
						cont = false;
						System.out.print("Goodbye!\n");
					}
				}
			}

			//Employee menu
			else if(option.equals("2")) {
				if(loggedInEmployee) {
					System.out.print("Please select from the following options:\n\n" +
							"A. Search a customer by name\n" +
							"B. Display all information\n" +
							"C. View orders by priority\n" +
							"D. Ship an order\n" +
							"E. List product databse\n" +
							"F. Add a new product\n" +
							"G. Remove a product\n" +
							"X. Quit\n" +
							"Enter your choice: ");
					String input = scan.nextLine();
					System.out.print("\n");

					//Search a customer by name
					if(input.equalsIgnoreCase("A")) {
						searchCustomer(customerHash);
					}
					//Display unsorted customer information including first
					//and last name, address, phone number, order history
					else if(input.equalsIgnoreCase("B")) {
						for(int i = 0; i < 32; i++) {
							customerHash.printBucket(i);
						}
					}
					//View orders by priority of shipment method
					else if(input.equalsIgnoreCase("C")) {
						ArrayList<Order> orderLists = orderHeap.sort();
						for(int i = orderLists.size()-1 ;i >= 0;i--){
							System.out.println(orderLists.get(i).toString());
						}
						orderHeap.buildHeap();
					}
					//Ship an order
					else if(input.equalsIgnoreCase("D")) {
						shipOrder(orderHeap,orderId);
						System.out.print("This order is now shipped!\n\n");
					}
					//List database of products
					else if(input.equalsIgnoreCase("E")) {
						System.out.print("Please select from the following options:\n\n" +
								"A. List data by title\n" +
								"B. List data by artist\n" +
								"Enter your choice: ");
						input = scan.nextLine();
						System.out.print("\n");
						//List data by primary key (title)
						if(input.equalsIgnoreCase("A")) {
							primaryBST.inOrderPrint();
						}
						//List data by secondary key (artist)
						else if(input.equalsIgnoreCase("B")) {
							secondaryBST.inOrderPrint();
						}
					}
					//Add a new product to database
					else if(input.equalsIgnoreCase("F")) {
						addProduct(primaryBST, secondaryBST, productStores);
					}
					//Remove a product from database
					else if(input.equalsIgnoreCase("G")) {
						removeProduct(primaryBST, secondaryBST, productStores);
					}
					//Quit
					else if(input.equalsIgnoreCase("X")) {
						cont = false;
						System.out.print("Goodbye!\n");
					}
				} else {
					System.out.print("You are not logged in as an employee yet.\n"
							+ "Please try to enter the store and log in again!\n");
				}
			}
		}
		BufferedWriter orderWriter = new BufferedWriter(new FileWriter("orders.txt"));
		for (int index = 0; index < orderHeap.getSize(); index++) {
			Order order = orderHeap.getElement(index);
			orderWriter.write(order.getId() + "\n");
			orderWriter.write(order.getOrderDate().format(formatter) + "\n");
			orderWriter.write(order.getStatus() + "\n");
			orderWriter.write(order.getQuantity() + "\n");
			orderWriter.write(order.getShipment().getSpeedCode() + "\n");
			orderWriter.write(order.getProduct() + "\n");
			orderWriter.write(order.getCustomer() + "\n");
			String tracking = "NA";
			if (order.getTrackingNumber() != null) {
				tracking = order.getTrackingNumber();
			}
			orderWriter.write(tracking + "\n");
			if (index < orderHeap.getSize()-1) {
				orderWriter.write("\n");
			}
		}
		orderWriter.close();

		BufferedWriter customersWriter = new BufferedWriter(new FileWriter("customers.txt"));
		for (int index = 0; index < customerList.size(); index++) {
			Customer customer = customerList.get(index);
			customersWriter.write(customer.getUserName() + "\n");
			customersWriter.write(customer.getPassWord() + "\n");
			customersWriter.write(customer.getFistName() + "\n");
			customersWriter.write(customer.getLastName() + "\n");
			customersWriter.write(customer.getAddress() + "\n");
			customersWriter.write(customer.getCity() + "\n");
			customersWriter.write(customer.getState() + "\n");
			customersWriter.write(customer.getZip() + "\n");
			customersWriter.write(customer.getPhone() + "\n");
			List<Order> orders = customer.getOrders();
			orders.pointIterator();
			String orderIds = "";
			while (!orders.offEnd()) {
				orderIds += orders.getIterator().getId();
				orders.advanceIterator();
				if (!orders.offEnd()) {
					orderIds += ",";
				}
			}
			customersWriter.write(orderIds + "\n");
			if (index < customerList.size()-1){
				customersWriter.write("\n");
			}
		}
		customersWriter.close();

		BufferedWriter productsWriter = new BufferedWriter(new FileWriter("products.txt"));
		for (int index = 0; index < productStores.size(); index++) {
			Product product = productStores.get(index);
			productsWriter.write("NA" + "\n");
			productsWriter.write(product.getTitle() + "\n");
			productsWriter.write(product.getArtist() + "\n");
			productsWriter.write(product.getPrice() + "\n");
			productsWriter.write(product.getNumberOfSongs() + "\n");

			List<String> songList = product.getSongList();
			songList.pointIterator();
			String songLists = "";
			while (!songList.offEnd()) {
				songLists += songList.getIterator();
				songList.advanceIterator();
				if (!songList.offEnd()) {
					songLists += ",";
				}
			}
			productsWriter.write(songLists + "\n");
			productsWriter.write(product.getReleaseDate().format(formatter) + "\n");
			productsWriter.write(product.getGenre() + "\n");
			if (index < productStores.size()-1) {
				productsWriter.write("\n");
			}

		}
		productsWriter.close();
	}

	public static void createAccount(Hash h, ArrayList l) {
		String username;
		String password;
		String firstName;
		String lastName;
		String address;
		String city;
		String state;
		int zip;
		String phoneNumber;

		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter your username: ");
		username = scan.nextLine();
		System.out.print("Please enter your password: ");
		password = scan.nextLine();
		System.out.print("Please enter your first name: ");
		firstName = scan.nextLine();
		System.out.print("Please enter your last name: ");
		lastName = scan.nextLine();
		System.out.print("Please enter your address: ");
		address = scan.nextLine();
		System.out.print("Please enter your city: ");
		city = scan.nextLine();
		System.out.print("Please enter your state: ");
		state = scan.nextLine();
		System.out.print("Please enter your zip code: ");
		zip = scan.nextInt();
		System.out.print("Please enter your phone number: ");
		scan.nextLine();
		phoneNumber = scan.nextLine();

		h.insert(new Customer(username, password, firstName, lastName, address, city, state,
				zip, phoneNumber, new List<Order>()));
		l.add(new Customer(username, password, firstName, lastName, address, city, state,
				zip, phoneNumber, new List<Order>()));
	}

	public static boolean idEmployee() {
		String username;
		String password;

		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter your username: ");
		username = scan.nextLine();
		System.out.print("Please enter your password: ");
		password = scan.nextLine();
		System.out.print("\n");

		//This is the only administrator account
		if(username.equals("user000") && password.equals("123456")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean searchProduct(BST bst, String key) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Product product = new Product(key, "", "", 0.0, 0, new List<String>(),
				LocalDate.now(), "");
		if(bst.search(product)) {
			return true;
		} else {
			return false;
		}
	}

	public static void searchCustomer(Hash h) {
		String firstName;
		String lastName;

		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter the customer's first name: ");
		firstName = scan.nextLine();
		System.out.print("Please enter the customer's last name: ");
		lastName = scan.nextLine();

		String name = firstName + lastName;
		h.searchName(name);
	}

	public static void shipOrder(Heap h,int orderId) {
		//int orderId;

		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter the ID of the order to ship: ");
		orderId = scan.nextInt();
		scan.nextLine();
		Order orderToShip = h.searchOrder(orderId);
		orderToShip.shipOrder();
		System.out.print("Please enter the tracking number: ");
		String tracking = scan.nextLine();
		orderToShip.setTrackingNumber(tracking);

		h.buildHeap();
	}

	public static void addProduct(BST bst1, BST bst2, ArrayList products) {
		String title;
		String artist;
		double price;
		int num;
		List<String> songList = new List<String>();
		String song;
		LocalDate releaseDate;
		String date;
		String genre;

		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter the title: ");
		title = scan.nextLine();
		System.out.print("Please enter the artist: ");
		artist = scan.nextLine();
		System.out.print("Please enter the price: ");
		price = scan.nextDouble();
		System.out.print("Please enter the number of songs: ");
		num = scan.nextInt();
		scan.nextLine();
		for(int i = 0; i < num; i++) {
			int j = i + 1;
			System.out.print("Please enter song #" + j + ": ");
			song = scan.nextLine();
			songList.addLast(song);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.print("Please enter the release date in dd/mm/yyyy: ");
		date = scan.nextLine();
		releaseDate = LocalDate.parse(date, formatter);
		System.out.print("Please enter the genre: ");
		genre = scan.nextLine();
		System.out.print("\nYou've add a new ablum!\n");

		Product product1 = new Product(title, title, artist, price,
				num, songList, releaseDate, genre);
		Product product2 = new Product(artist, title, artist, price,
				num, songList, releaseDate, genre);
		bst1.insert(product1);
		products.add(product1);
		bst2.insert(product2);
	}

	public static void removeProduct(BST bst1, BST bst2, ArrayList products) {
		String title;
		String artist;

		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter the title of the product that you want to remove: ");
		title = scan.nextLine();
		System.out.print("Please enter the artist of the product that you want to remove: ");
		artist = scan.nextLine();

		Product product1 = new Product(title, "", "", 0.0, 0, new List<String>(), LocalDate.now(),"");
		Product product2 = new Product(artist, "", "", 0.0, 0, new List<String>(), LocalDate.now(),"");
		if(!bst1.search(product1)) {
			System.out.println("There's no " + title + " in the database.\n");
		} else {
			bst1.remove(product1);
			products.remove(product1);
			bst2.remove(product2);
			System.out.println("\n" + title + " was removed!\n");
		}
	}
}
