package com.wYne.automation.ui.elements;

import com.wYne.automation.ui.elements.impl.TableImpl;
import com.wYne.automation.ui.internal.ImplementedBy;
import org.openqa.selenium.WebElement;

/**
 * Table functionality.
 */
@ImplementedBy(TableImpl.class)
public interface Table extends SlWebElement {

	/**
     * Gets the number of rows in the table
     * @return int equal to the number of rows in the table
     */
    int getRowCount();

    /**
     * Gets the number of columns in the table
     * @return int equal to the number of rows in the table
     */
    int getColumnCount();

    /**
     * Gets the WebElement of the cell at the specified index
     * @param rowIdx The zero based index of the row
     * @param colIdx The zero based index of the column
     * @return the WebElement of the cell at the specified index
     */
    WebElement getCellAtIndex(int rowIdx, int colIdx); 
}
