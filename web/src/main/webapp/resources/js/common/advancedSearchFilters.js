/**
 * DataTypes for Advanced search view
 */
define([
    'underscore',
    'common/dateFormatter',
    'common/messages'
], function (_, dateFormatter, messages) {

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
            shortLabel: messages["advanced_filter_equals_short"],
            label: messages["advanced_filter_equals"],
            suffix: "EQ"
        }),
        NOT_EQUALS: new Comparison({
            shortLabel: messages["advanced_filter_not_equals_short"],
            label: messages["advanced_filter_not_equals"],
            suffix: "NEQ"
        }),
        LIKE: new Comparison({
            shortLabel: messages["advanced_filter_like_short"],
            label: messages["advanced_filter_like"],
            suffix: "LIKE"
        }),
        NOT_LIKE: new Comparison({
            shortLabel: messages["advanced_filter_not_like_short"],
            label: messages["advanced_filter_not_like"],
            suffix: "NLIKE"
        }),
        GREATER_OR_EQUALS: new Comparison({
            shortLabel: messages["advanced_filter_greater_or_equals_short"],
            label: messages["advanced_filter_greater_or_equals"],
            suffix: "GE"
        }),
        LESS_OR_EQUALS: new Comparison({
            shortLabel: messages["advanced_filter_less_or_equals_short"],
            label: messages["advanced_filter_less_or_equals"],
            suffix: "LE"
        }),
        GREATER: new Comparison({
            shortLabel: messages["advanced_filter_greater_short"],
            label: messages["advanced_filter_greater"],
            suffix: "GT"
        }),
        LESS: new Comparison({
            shortLabel: messages["advanced_filter_less_short"],
            label: messages["advanced_filter_less"],
            suffix: "LT"
        }),
        BETWEEN: new Comparison({
            shortLabel: messages["advanced_filter_between_short"],
            label: messages["advanced_filter_between"],
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
                return dateFormatter(value, messages["common_dateFormat"]).isValid();
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
