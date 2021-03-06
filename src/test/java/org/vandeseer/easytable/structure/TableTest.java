package org.vandeseer.easytable.structure;

import org.junit.Test;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.CellBaseData;
import org.vandeseer.easytable.structure.cell.CellText;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TableTest {

    @Test
    public void getNumberOfColumns_tableBuilderWithThreeColumns() {
        final TableBuilder tableBuilder = Table.builder()
                .addColumnOfWidth(12)
                .addColumnOfWidth(34)
                .addColumnOfWidth(56);
        final Table table = tableBuilder.build();

        assertThat(table.getNumberOfColumns(), equalTo(3));
    }

    @Test
    public void getWidth_tableBuilderWithTwoColumns() {
        final TableBuilder tableBuilder = Table.builder()
                .addColumnOfWidth(20)
                .addColumnOfWidth(40);
        final Table table = tableBuilder.build();

        assertThat(table.getWidth(), equalTo(60f));
    }

    @Test
    public void getRows_tableBuilderWithOneRow() {
        final TableBuilder tableBuilder = Table.builder();
        tableBuilder.addColumnOfWidth(12)
                .addColumnOfWidth(34);
        final Row row = Row.builder()
                .add(CellText.builder().text("11").build())
                .add(CellText.builder().text("12").build())
                .build();
        tableBuilder.addRow(row);
        final Table table = tableBuilder.build();

        assertThat(table.getRows().size(), equalTo(1));
    }

    @Test
    public void getHeight_twoRowsWithDifferentPaddings() {
        final TableBuilder tableBuilder = Table.builder();
        tableBuilder.addColumnOfWidth(12)
                .addColumnOfWidth(34);
        final Row row = Row.builder()
                .add(CellText.builder().text("11").paddingTop(35).paddingBottom(15).build())
                .add(CellText.builder().text("12").paddingTop(15).paddingBottom(25).build())
                .build();
        tableBuilder.addRow(row);
        final Table table = tableBuilder
                .fontSize(12) // this will have a font height withText 13.872
                .build();

        // highest cell (60) + font height
        assertThat(table.getHeight(), equalTo(58.616f));
    }

    @Test
    public void tableBuilder_shouldConnectStructureCorrectly() {
        // We are spanning two columns in the first cell
        CellBaseData lastCell = CellText.builder().text("").build();

        Table table = Table.builder()
                .addColumnOfWidth(10)
                .addColumnOfWidth(20)
                .addColumnOfWidth(30)
                .addRow(Row.builder()
                    .add(CellText.builder().text("").span(2).build())
                    .add(lastCell)
                    .build())
                .build();

        Column lastColumn = table.getColumns().get(table.getColumns().size() - 1);

        // So make sure the second cell is connected correctly to the last column
        assertThat(lastCell.getColumn(), is(lastColumn));
    }

}
