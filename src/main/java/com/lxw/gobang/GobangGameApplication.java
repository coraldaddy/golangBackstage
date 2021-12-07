package com.lxw.gobang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lxw.gobang.mapper")
public class GobangGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GobangGameApplication.class, args);
	}

}
