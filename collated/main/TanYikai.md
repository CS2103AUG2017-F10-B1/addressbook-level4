# TanYikai
###### \java\seedu\address\logic\commands\SortCommand.java
``` java

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.ParserUtil.Option;

/**
 * Lists all sorted persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";
    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private Option sortOption;

    /**
     * @param sortOption is the option in which person is sorted by
     * default is by name
     * 0,1,2,3,4 represents sort by name, phone, email, address, remark respectively
     */
    public SortCommand(Option sortOption) {
        requireNonNull(sortOption);
        this.sortOption = sortOption;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersons(sortOption);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses the String to return int according to the corresponding prefix
     * 0,1,2,3,4 corresponds to PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK respectively
     * @throws IllegalValueException if the specified prefix is invalid (not n/, p/, e/, a/ or r/).
     */
    public static Option parseSortOption(String prefix) throws IllegalValueException {
        String trimmedPrefix = prefix.trim();
        Option sortOption;

        if (trimmedPrefix.equals(PREFIX_NAME.toString())) {
            sortOption = Option.NAME;
        } else if (trimmedPrefix.equals(PREFIX_PHONE.toString())) {
            sortOption = Option.PHONE;
        } else if (trimmedPrefix.equals(PREFIX_EMAIL.toString())) {
            sortOption = Option.EMAIL;
        } else if (trimmedPrefix.equals(PREFIX_ADDRESS.toString())) {
            sortOption = Option.ADDRESS;
        } else if (trimmedPrefix.equals(PREFIX_REMARK.toString())) {
            sortOption = Option.REMARK;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_PREFIX);
        }
        return sortOption;
    }

    /**
     * The enum for the various sort options available.
     * NAME, PHONE, EMAIL, ADDRESS, REMARK means sort by name, phone, eail, address or remark respectively
     */
    public enum Option {
        NAME, PHONE, EMAIL, ADDRESS, REMARK
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * If {@code phone} is not present, {@code String Unspecified phone number} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parseAddPhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(Phone.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * If {@code address} is not present, {@code String Unspecified address} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(Address.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * If {@code email} is not present, {@code String Unspecified email} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseAddEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(Email.UNSPECIFIED);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * If {@code remark} is not present, {@code String Unspecified remark} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseAddRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.of(Remark.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
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
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     *Sorts the persons object according to the sortOption.
     * 0,1,2,3,4 represents name, phone, email, address, remark respectively
     */
    public void sortData(ParserUtil.Option sortOption) {
        persons.sort(sortOption);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPersons(ParserUtil.Option sortOption) {
        addressBook.sortData(sortOption);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * The default Address constructor when address is not specified by the user
     */
    private Address() {
        value = "Unspecified address";
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Sets the sort option using integer
     * 0,1,2,3,4 represents name, phone, email, address and remark respectively
     */
    public void setSortOption(Option option) {
        this.sortOption = option;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * The compareTo method compares different attribute of the person object according to sortOption number
     * The default is compare by name
     */
    @Override
    public int compareTo(Person o) {
        if (sortOption == Option.PHONE) {
            return this.getPhone().toString().compareToIgnoreCase(o.getPhone().toString());
        } else if (sortOption == Option.EMAIL) {
            return this.getEmail().toString().compareToIgnoreCase(o.getEmail().toString());
        } else if (sortOption == Option.ADDRESS) {
            return this.getAddress().toString().compareToIgnoreCase(o.getAddress().toString());
        } else if (sortOption == Option.REMARK) {
            return this.getRemark().toString().compareToIgnoreCase(o.getRemark().toString());
        }
        return this.getName().toString().compareToIgnoreCase(o.getName().toString());
    }
```
###### \java\seedu\address\model\person\Phone.java
``` java
    /**
     * The default Phone constructor when phone is not specified by the user
     */
    private Phone() {
        value = "Unspecified phone number";
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final Remark UNSPECIFIED = new Remark();
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    private Remark() {
        value = "Unspecified remark";
    }

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the persons object according to the sortOption.
     * 0,1,2,3,4 represents name, phone, email, address, remark respectively
     */
    public void sort(Option sortOption) {
        requireNonNull(internalList);
        if (sortOption == Option.NAME) {
            for (Person p: internalList) {
                p.setSortOption(Option.NAME);
            }
        } else if (sortOption == Option.PHONE) {
            for (Person p: internalList) {
                p.setSortOption(Option.PHONE);
            }
        } else if (sortOption == Option.EMAIL) {
            for (Person p: internalList) {
                p.setSortOption(Option.EMAIL);
            }
        } else if (sortOption == Option.ADDRESS) {
            for (Person p: internalList) {
                p.setSortOption(Option.ADDRESS);
            }
        } else if (sortOption == Option.REMARK) {
            for (Person p: internalList) {
                p.setSortOption(Option.REMARK);
            }
        }

        Collections.sort(internalList);
    }
```
