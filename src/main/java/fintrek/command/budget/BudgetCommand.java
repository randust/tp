package fintrek.command.budget;

import fintrek.budget.BudgetManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandInfo(
        description = """
                Format: /budget $<AMOUNT>
                AMOUNT must be a positive number greater than 0
                Example: /budget $500 - sets the monthly spending budget to $500.
                """
)

public class BudgetCommand extends Command {
    private static final String COMMAND_NAME = "budget";
    public BudgetCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        if (InputValidator.isNullOrBlank(arguments)) {
            return new CommandResult(false,
                    String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME));
        }

        Pattern pattern = Pattern.compile("\\$\\s*(\\d+(?:\\.\\d{1,2})?)");
        Matcher matcher = pattern.matcher(arguments.trim());

        if (!matcher.matches()) {
            return new CommandResult(false,
                    String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
        }

        String amountStr = matcher.group(1);
        if (!InputValidator.isValidPositiveDouble(amountStr)) {
            return new CommandResult(false, MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        double amount = Double.parseDouble(amountStr);
        BudgetManager.getInstance().setBudget(amount);

        return new CommandResult(true,
                String.format(MessageDisplayer.SET_BUDGET_SUCCESS_MESSAGE_TEMPLATE, amount));
    }
}
