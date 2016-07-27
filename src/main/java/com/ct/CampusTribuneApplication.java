package com.ct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ct.algorithms.SpamFilter;

@SpringBootApplication
public class CampusTribuneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusTribuneApplication.class, args);
		SpamFilter.train();
		System.out.println(SpamFilter.wordStatsMap);
	}
}
