package com.project.microservicio.commons.direcciones.models.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestOrdenar {
	
	public void ordenarList(List<Integer> lista) {
		//eliminar repetidos
		lista = new ArrayList<>(new HashSet(lista));
		
		//Obtener los pares sin repeticion
		List<Integer> numeros = Stream.of(lista).collect(Collectors.flatMapping(l -> l.stream().filter(i -> i % 2 ==0), Collectors.toList()));
		
		//Ordenar 
		numeros.sort(Comparator.comparing(i->i));
		
		System.out.println(numeros);
		
	}
	
	public static void main(String[] args) {
		TestOrdenar t = new TestOrdenar();
		t.ordenarList(new ArrayList<>(List.of(10,7,5,1,3,4,6,8,9,9,1,2,3,4,5)));
	}
}
