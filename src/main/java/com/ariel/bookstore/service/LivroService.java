package com.ariel.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ariel.bookstore.domain.Categoria;
import com.ariel.bookstore.domain.Livro;
import com.ariel.bookstore.repositories.LivroRepository;
import com.ariel.bookstore.service.exception.DataIntegrityViolationException;
import com.ariel.bookstore.service.exception.ObjectNotFoundException;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private CategoriaService categoriaService;
	
	public Livro findById(Integer id) {
		Optional<Livro> obj = livroRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Livro.class.getName()));
	}

	public List<Livro> findAllByCategoria(Integer id) {
		Categoria obj = categoriaService.findById(id);
		return obj.getLivros();
	}

	public Livro create(Integer id_cat, Livro obj) {
		obj.setId(null);
		Categoria cat = categoriaService.findById(id_cat);
		obj.setCategoria(cat);
		return livroRepository.save(obj);
	}

	public Livro update(Integer id, Livro livro) {
		Livro obj = findById(id);
		updateData(livro, obj);
		return livroRepository.save(obj);
	}

	private void updateData(Livro livro, Livro obj) {
		obj.setTitulo(livro.getTitulo());
		obj.setNomeAutor(livro.getNomeAutor());
		obj.setTexto(livro.getTexto());
	}

	public void delete(Integer id) {
		Livro obj = findById(id);
		livroRepository.delete(obj);
		
	}



}
