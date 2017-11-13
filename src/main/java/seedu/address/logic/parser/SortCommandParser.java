package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.Option;
import static seedu.address.logic.parser.ParserUtil.parseSortOption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author TanYikai
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        Option sortOption;

        try {
            sortOption = parseSortOption(args);

            return new SortCommand(sortOption);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
