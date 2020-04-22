package entidade;

import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, java.sql.Date> {
	
	@Override
    public java.sql.Date convertToDatabaseColumn(LocalDate locDate) {
        return locDate == null ? null : java.sql.Date.valueOf(locDate);
    }
 
    @Override
    public LocalDate convertToEntityAttribute(java.sql.Date sqlDate) {
        return sqlDate == null ? null : sqlDate.toLocalDate();
        
    }

}
