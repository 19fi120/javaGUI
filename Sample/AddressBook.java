package Sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AddressBook {
    private ArrayList<Address> book;
   
    public AddressBook() {
    	book = new ArrayList<Address>();
    }

    public void open(String filename) { // ファイルからの読み込み
		try {
		    book.clear();
		    File file = new File(filename);
	        BufferedReader reader = new BufferedReader(new FileReader(file));
		    String line;
	        while((line = reader.readLine()) != null) {
	            String[] field = line.split(",");
	            add(new Address(field[0], field[1], field[2], field[3]));
	        }
		    reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void save(String filename) { // ファイルへの書き出し
		try {
		    File file = new File(filename);
		    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		    for (Address address : book) {
		    	writer.println(address);
		    } 
		    writer.close();
		}catch(Exception e) {
		    e.printStackTrace();
		}
    }

    public void add(Address address) { // アドレスの追加
    	book.add(address);
    }

    public void remove(Address address) { // アドレスの削除
    	book.remove(address);
    }

    public void showAddresses() { // 一覧をプリント文で表示
		for (Address address : book) {
		    System.out.println(address);
		}
    }

    public Address findName(String name) { // name から探す
		for (Address address : book) {
		    if (name.equals(address.getName())) {
		    	return address;
		    }
		}
		return null;
    }

    public Address findAddress(String add) { // add から探す
		for (Address address : book) {
		    if (add.equals(address.getAddress())) {
		    	return address;
		    }
		}
		return null;
    }

    public Address findTel(String tel) { // tel から探す
		for (Address address : book) {
		    if (tel.equals(address.getTel())) {
		    	return address;
		    }
		}
		return null;
    }

    public Address findEmail(String email) { // email から探す
		for (Address address : book) {
		    if (email.equals(address.getEmail())) {
		    	return address;
		    }
		}
		return null;
    }

    public ArrayList<String> getNames() { // 名前の一覧を得る
		ArrayList<String> nameList = new ArrayList<String>();
		for (Address address : book) {
		    nameList.add( address.getName() );
		}
		return nameList;
    }

    public static void main(String[] args) { // 使ってみる
		AddressBook book = new AddressBook();
		book.open("address.txt");
		book.showAddresses();
		Address taroAddress = new Address("電大太郎", "東京都千代田区", "03-5280-XXXX", "taro@dendai.ac.jp");
		book.add( taroAddress );
		book.showAddresses();
		String tmp = "電大太郎";
		System.out.println(tmp + "を探すと" + book.findName(tmp));
		tmp = "東京都千代田区";
		System.out.println(tmp + "を探すと" + book.findAddress(tmp));
		tmp = "03-5280-XXXX";
		System.out.println(tmp + "を探すと" + book.findTel(tmp));
		tmp = "taro@dendai.ac.jp";
		System.out.println(tmp + "を探すと" + book.findEmail(tmp));
		tmp = "電大花子";
		System.out.println(tmp + "を探すと" + book.findName(tmp));
		book.remove( taroAddress );
		book.showAddresses();
		book.save("address2.txt");
    }

}