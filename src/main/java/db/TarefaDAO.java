package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Tarefa;
import model.Tarefa;
import model.Tarefa;

public class TarefaDAO {
	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaTarefa;

	public TarefaDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaTarefa = workbook.getSheet("Tarefa");
	}

	public List<Tarefa> retornarListaTarefas() {

		List<Tarefa> listaTarefas = new ArrayList<Tarefa>();
		Iterator<Row> iterator = abaTarefa.iterator();

		Row nextRow;
		if (abaTarefa.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Tarefa tarefa = new Tarefa();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						tarefa.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						tarefa.setTitulo(nextCell.getStringCellValue());
						break;

					case 2:
						tarefa.setIdRequisito((int) nextCell.getNumericCellValue());
						break;

					case 3:
						tarefa.setIdDesenvolvedor((int) nextCell.getNumericCellValue());
						break;

					case 4:
						tarefa.setPrioridade(nextCell.getStringCellValue());
						break;

					case 5:
						tarefa.setNivelImpacto(nextCell.getStringCellValue());
						break;

					case 6:
						tarefa.setIdRelease((int) nextCell.getNumericCellValue());
						break;

					}

				}
				listaTarefas.add(tarefa);
			}
		}

		return listaTarefas;
	}

	public void addNewTarefa(Tarefa tarefa) {
		Iterator<Row> iterator = abaTarefa.iterator();
		int count = 0;

		while (abaTarefa.getRow(count) != null) {
			count++;
		}

		Row row = abaTarefa.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(tarefa.getTitulo());

		cell = row.createCell(2);
		cell.setCellValue(tarefa.getIdRequisito());

		cell = row.createCell(3);
		cell.setCellValue(tarefa.getIdDesenvolvedor());

		cell = row.createCell(4);
		cell.setCellValue(tarefa.getPrioridade());

		cell = row.createCell(5);
		cell.setCellValue(tarefa.getNivelImpacto());

		xlsDAO.writeAndCloseXls();
	}

	public List<Tarefa> retornarListaTarefasDisponiveis() {
		List<Tarefa> tarefasDisponiveis = new ArrayList<Tarefa>();
		List<Tarefa> listaTarefas = retornarListaTarefas();

		for (Tarefa tarefa : listaTarefas) {
			if (tarefa.getIdRelease() == null || tarefa.getIdRelease() == 0) {
				tarefasDisponiveis.add(tarefa);
			}
		}

		return tarefasDisponiveis;
	}

	public List<Tarefa> retornarListaTarefasVinculados(int idRelease) {

		List<Tarefa> tarefasDisponiveis = new ArrayList<Tarefa>();
		List<Tarefa> listaTarefas = retornarListaTarefas();

		for (Tarefa tarefa : listaTarefas) {
			if (!(tarefa.getIdRelease() == null)) {
				if (tarefa.getIdRelease().equals(idRelease))
					tarefasDisponiveis.add(tarefa);
			}
		}

		return tarefasDisponiveis;

	}

	public void vincularTarefaARelease(List<Tarefa> listaTarefasVinculadosRelease, int idRelease)
			throws InterruptedException {

		abaTarefa = null;
		abaTarefa = workbook.getSheet("Tarefa");

		Iterator<Row> iterator = abaTarefa.iterator();
		Row nextRow;

		while (iterator.hasNext()) {
			nextRow = iterator.next();
			if (nextRow.getRowNum() == 0)
				nextRow = iterator.next();

			for (Tarefa tarefa : listaTarefasVinculadosRelease) {
				if (tarefa.getId() == (int) nextRow.getCell(0).getNumericCellValue()) {
					Cell cell = nextRow.createCell(6);
					cell.setCellValue(idRelease);
				}
			}
		}
		xlsDAO.writeAndCloseXls();
	}

	public void desvincularTarefaARelease(List<Tarefa> listaTarefasDesvinculadosRelease) throws InterruptedException {
		abaTarefa = null;
		abaTarefa = workbook.getSheet("Tarefa");
		Iterator<Row> iterator = abaTarefa.iterator();
		Row nextRow;

		while (iterator.hasNext()) {
			nextRow = iterator.next();
			if (nextRow.getRowNum() == 0)
				nextRow = iterator.next();

			for (Tarefa tarefa : listaTarefasDesvinculadosRelease) {
				if (tarefa.getId() == (int) nextRow.getCell(0).getNumericCellValue()) {
					nextRow.createCell(6);
				}
			}
		}
		xlsDAO.writeAndCloseXls();
	}
}
