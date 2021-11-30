package com.team418.api.user;

import com.team418.api.user.dto.CreateLibrarianDto;
import com.team418.api.user.dto.LibrarianDto;
import com.team418.domain.user.Librarian;

public class LibrarianMapper {
    public static LibrarianDto modelToDto(Librarian librarian){
        return new LibrarianDto(librarian.getUniqueId(), librarian.getFirstName(), librarian.getLastName(), librarian.getEmail());
    }

    public static Librarian dtoToModel(CreateLibrarianDto librarianDto){
        return new Librarian(librarianDto.firstName(),librarianDto.lastName(),librarianDto.email());
    }
}
