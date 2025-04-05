package fintrek.command.add;

import java.time.LocalDate;

public record AddParseResult(String desc, double amount, String category, LocalDate date) {
}
