package com.example.demo.db;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.entity.Account;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "com/example/demo/db/sqlMapConfig.xml";
			InputStream inputStream = org.apache.ibatis.io.Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			System.out.println("예외발생:" + e.getMessage());
		}
	}

	public static Account emailCheckWithEmail(String email, String id) {
		SqlSession session = sqlSessionFactory.openSession();
		Account a = null;
		HashMap<String, String> params = new HashMap<>();
		params.put("email", email);
		params.put("id", id);
		System.out.println("DBManager id:" + id);
		System.out.println("DBManager email:" + email);
		a = session.selectOne("account.emailCheckWithEmail", params);
		session.close();
		return a;
	}

	public static Account findByEmail(String email) {
		SqlSession session = sqlSessionFactory.openSession();
		Account a = null;
		a = session.selectOne("account.findByEmail", email);
		session.close();
		return a;
	}

	public static Account findByAid(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		Account a = null;
		a = session.selectOne("account.findByAid", id);
		session.close();
		return a;
	}
	
	 public static int update(Account a) {
	      SqlSession session = sqlSessionFactory.openSession();
	      int result = 0;
	      result = session.update("account.update", a);
	      session.commit();
	      session.close();
	      return result;
	   }
	   public static int deleteAccount(String aid) {
		      SqlSession session = sqlSessionFactory.openSession();
		      int re = session.delete("account.deleteAccount", aid);
		      session.commit();
		      session.close();
		      return re;
		   }
}
