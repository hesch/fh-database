package de.randomerror.fh.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for representing a database district
 * it is the equivalent for 'lieferbezirk'
 */
@Data
@AllArgsConstructor
public class District {
    private int id;
    private String plz;
}
