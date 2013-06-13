/**
 * DataTypes for Advanced search view
 */
define([
    'underscore',
    'common/dateFormatter'
], function (_, dateFormatter) {

    /* Comparison */
    var Comparison = function(options) {
        this.shortLabel = options.shortLabel;
        this.label = options.label + " " + this.shortLabel;
        this.suffix = options.suffix;
    };

    _.extend(Comparison.prototype, {
        contains: function(fieldName) {
            return fieldName && fieldName.indexOf(this.suffix) == fieldName.length - this.suffix.length;
        }
    });

    var COMPARISONS = {
        EQUALS: new Comparison({
            shortLabel: "=",
            label: "Равно",
            suffix: "EQ"
        }),
        NOT_EQUALS: new Comparison({
            shortLabel: "<>",
            label: "Не равно",
            suffix: "NEQ"
        }),
        LIKE: new Comparison({
            shortLabel: "~",
            label: "Содержит",
            suffix: "LK"
        }),
        NOT_LIKE: new Comparison({
            shortLabel: "!~",
            label: "Не содержит",
            suffix: "NLK"
        }),
        GREATER_OR_EQUALS: new Comparison({
            shortLabel: ">=",
            label: "Больше или равно",
            suffix: "GE"
        }),
        LESS_OR_EQUALS: new Comparison({
            shortLabel: "<=",
            label: "Меньше или равно",
            suffix: "LE"
        }),
        GREATER: new Comparison({
            shortLabel: ">",
            label: "Больше",
            suffix: "GR"
        }),
        LESS: new Comparison({
            shortLabel: "<",
            label: "Меньше",
            suffix: "LESS"
        }),
        BETWEEN: new Comparison({
            shortLabel: ":",
            label: "В диапазоне",
            suffix: "BTWN"
        })
    };

    /* Data type */
    var DataType = function(options) {
        this.type = options.type;
        this.isDate = options.isDate;
        this.validator = options.validator;
        this.comparisons = options.comparisons;
        this.formatValue = options.formatValue;
    };

    _.extend(DataType.prototype, {
        findComparison: function(fieldName) {
            return _.find(this.comparisons, function(comparison) {
                return comparison.contains(fieldName)
            });
        }
    });

    var DATA_TYPES = {
        DATE: new DataType({
            isDate: true,
            validator: function(value) {
                return dateFormatter(value, "YYYY-MM-DD").isValid();
            },
            comparisons: [
                COMPARISONS.EQUALS,
                COMPARISONS.NOT_EQUALS,
                COMPARISONS.GREATER_OR_EQUALS,
                COMPARISONS.LESS_OR_EQUALS,
                COMPARISONS.GREATER,
                COMPARISONS.LESS,
                COMPARISONS.BETWEEN
            ]
        }),
        NUMBER: new DataType({
            validator: function(value) {
                return value && "" + parseInt(value, 10) === value;
            },
            comparisons: [
                COMPARISONS.EQUALS,
                COMPARISONS.NOT_EQUALS,
                COMPARISONS.GREATER_OR_EQUALS,
                COMPARISONS.LESS_OR_EQUALS,
                COMPARISONS.GREATER,
                COMPARISONS.LESS,
                COMPARISONS.BETWEEN
            ]
        }),
        STRING: new DataType({
            validator: function(value) {
                return true;
            },
            comparisons: [
                COMPARISONS.LIKE,
                COMPARISONS.NOT_LIKE
            ]
        })
    };

    /* Single Filter */
    var Filter = function(field, label, dataType) {
        this.field = field;
        this.label = label;
        this.dataType = dataType;
    };

    /* Advanced Filters */
    var AdvancedFilters = function(filters) {
        this.filters = filters;
    };

    AdvancedFilters.Filter = Filter;

    _.extend(AdvancedFilters, DATA_TYPES);

    _.extend(AdvancedFilters.prototype, {

        find: function(fieldName) {
            var comparison;
            var filter = _.find(this.filters, function(filter) {
                if (fieldName.indexOf(filter.field) == 0) {
                    comparison = filter.dataType.findComparison(fieldName);
                    return comparison
                        ? (filter.field + comparison.suffix === fieldName ? comparison : false)
                        : false;
                }

                return false;
            });

            return filter ? {filter: filter, comparison: comparison} : undefined;
        },

        findByName: function(fieldName) {
            return _.find(this.filters, function(filter) {
                return fieldName === filter.field;
            });
        },

        findFirst: function(fieldNames) {
            var filter;

            for (var i = 0; i < fieldNames.length; i++) {
                filter = this.find(fieldNames[i]);
                if (filter) {
                    return filter;
                }
            }

            return undefined;
        },

        findComparisonBySuffix: function(filter, suffix) {
            return _.find(filter.dataType.comparisons, function(comparison) {
                return comparison.suffix === suffix;
            })
        }
    });

    return AdvancedFilters;
});
