(function(a, b) {
	if (a.cleanData) {
		var c = a.cleanData;
		a.cleanData = function(b) {
			for (var d = 0, e;
			(e = b[d]) != null; d++) try {
				a(e).triggerHandler("remove")
			} catch (f) {}
			c(b)
		}
	} else {
		var d = a.fn.remove;
		a.fn.remove = function(b, c) {
			return this.each(function() {
				return c || (!b || a.filter(b, [this]).length) && a("*", this).add([this]).each(function() {
					try {
						a(this).triggerHandler("remove")
					} catch (b) {}
				}), d.call(a(this), b, c)
			})
		}
	}
	a.widget = function(b, c, d) {
		var e = b.split(".")[0],
			f;
		b = b.split(".")[1], f = e + "-" + b, d || (d = c, c = a.Widget), a.expr[":"][f] = function(c) {
			return !!a.data(c, b)
		}, a[e] = a[e] || {}, a[e][b] = function(a, b) {
			arguments.length && this._createWidget(a, b)
		};
		var g = new c;
		g.options = a.extend(!0, {}, g.options), a[e][b].prototype = a.extend(!0, g, {
			namespace: e,
			widgetName: b,
			widgetEventPrefix: a[e][b].prototype.widgetEventPrefix || b,
			widgetBaseClass: f
		}, d), a.widget.bridge(b, a[e][b])
	}, a.widget.bridge = function(c, d) {
		a.fn[c] = function(e) {
			var f = typeof e == "string",
				g = Array.prototype.slice.call(arguments, 1),
				h = this;
			return e = !f && g.length ? a.extend.apply(null, [!0, e].concat(g)) : e, f && e.charAt(0) === "_" ? h : (f ? this.each(function() {
				var d = a.data(this, c),
					f = d && a.isFunction(d[e]) ? d[e].apply(d, g) : d;
				if (f !== d && f !== b) return h = f, !1
			}) : this.each(function() {
				var b = a.data(this, c);
				b ? b.option(e || {})._init() : a.data(this, c, new d(e, this))
			}), h)
		}
	}, a.Widget = function(a, b) {
		arguments.length && this._createWidget(a, b)
	}, a.Widget.prototype = {
		widgetName: "widget",
		widgetEventPrefix: "",
		options: {
			disabled: !1
		},
		_createWidget: function(b, c) {
			a.data(c, this.widgetName, this), this.element = a(c), this.options = a.extend(!0, {}, this.options, this._getCreateOptions(), b);
			var d = this;
			this.element.bind("remove." + this.widgetName, function() {
				d.destroy()
			}), this._create(), this._trigger("create"), this._init()
		},
		_getCreateOptions: function() {
			return a.metadata && a.metadata.get(this.element[0])[this.widgetName]
		},
		_create: function() {},
		_init: function() {},
		destroy: function() {
			this.element.unbind("." + this.widgetName).removeData(this.widgetName), this.widget().unbind("." + this.widgetName).removeAttr("aria-disabled").removeClass(this.widgetBaseClass + "-disabled " + "ui-state-disabled")
		},
		widget: function() {
			return this.element
		},
		option: function(c, d) {
			var e = c;
			if (arguments.length === 0) return a.extend({}, this.options);
			if (typeof c == "string") {
				if (d === b) return this.options[c];
				e = {}, e[c] = d
			}
			return this._setOptions(e), this
		},
		_setOptions: function(b) {
			var c = this;
			return a.each(b, function(a, b) {
				c._setOption(a, b)
			}), this
		},
		_setOption: function(a, b) {
			return this.options[a] = b, a === "disabled" && this.widget()[b ? "addClass" : "removeClass"](this.widgetBaseClass + "-disabled" + " " + "ui-state-disabled").attr("aria-disabled", b), this
		},
		enable: function() {
			return this._setOption("disabled", !1)
		},
		disable: function() {
			return this._setOption("disabled", !0)
		},
		_trigger: function(b, c, d) {
			var e, f, g = this.options[b];
			d = d || {}, c = a.Event(c), c.type = (b === this.widgetEventPrefix ? b : this.widgetEventPrefix + b).toLowerCase(), c.target = this.element[0], f = c.originalEvent;
			if (f) for (e in f) e in c || (c[e] = f[e]);
			return this.element.trigger(c, d), !(a.isFunction(g) && g.call(this.element[0], c, d) === !1 || c.isDefaultPrevented())
		}
	}
})(jQuery);;
(function(a, b) {
	var c = "ui-dialog ui-widget ui-widget-content ui-corner-all ",
		d = {
			buttons: !0,
			height: !0,
			maxHeight: !0,
			maxWidth: !0,
			minHeight: !0,
			minWidth: !0,
			width: !0
		},
		e = {
			maxHeight: !0,
			maxWidth: !0,
			minHeight: !0,
			minWidth: !0
		};
	a.widget("ui.dialog", {
		options: {
			autoOpen: !0,
			buttons: {},
			closeOnEscape: !0,
			closeText: "close",
			dialogClass: "",
			draggable: !0,
			hide: null,
			height: "auto",
			maxHeight: !1,
			maxWidth: !1,
			minHeight: 150,
			minWidth: 150,
			modal: !1,
			position: {
				my: "center",
				at: "center",
				collision: "fit",
				using: function(b) {
					var c = a(this).css(b).offset().top;
					c < 0 && a(this).css("top", b.top - c)
				}
			},
			resizable: !0,
			show: null,
			stack: !0,
			title: "",
			width: 300,
			zIndex: 1e3
		},
		_create: function() {
			this.originalTitle = this.element.attr("title"), typeof this.originalTitle != "string" && (this.originalTitle = ""), this.options.title = this.options.title || this.originalTitle;
			var b = this,
				d = b.options,
				e = d.title || "&#160;",
				f = a.ui.dialog.getTitleId(b.element),
				g = (b.uiDialog = a("<div></div>")).appendTo(document.body).hide().addClass(c + d.dialogClass).css({
					zIndex: d.zIndex
				}).attr("tabIndex", -1).css("outline", 0).keydown(function(c) {
					d.closeOnEscape && !c.isDefaultPrevented() && c.keyCode && c.keyCode === a.ui.keyCode.ESCAPE && (b.close(c), c.preventDefault())
				}).attr({
					role: "dialog",
					"aria-labelledby": f
				}).mousedown(function(a) {
					b.moveToTop(!1, a)
				}),
				h = b.element.show().removeAttr("title").addClass("ui-dialog-content ui-widget-content").appendTo(g),
				i = (b.uiDialogTitlebar = a("<div></div>")).addClass("ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix").prependTo(g),
				j = a('<a href="#"></a>').addClass("ui-dialog-titlebar-close ui-corner-all").attr("role", "button").hover(function() {
					j.addClass("ui-state-hover")
				}, function() {
					j.removeClass("ui-state-hover")
				}).focus(function() {
					j.addClass("ui-state-focus")
				}).blur(function() {
					j.removeClass("ui-state-focus")
				}).click(function(a) {
					return b.close(a), !1
				}).appendTo(i),
				k = (b.uiDialogTitlebarCloseText = a("<span></span>")).addClass("ui-icon ui-icon-closethick").text(d.closeText).appendTo(j),
				l = a("<span></span>").addClass("ui-dialog-title").attr("id", f).html(e).prependTo(i);
			a.isFunction(d.beforeclose) && !a.isFunction(d.beforeClose) && (d.beforeClose = d.beforeclose), i.find("*").add(i).disableSelection(), d.draggable && a.fn.draggable && b._makeDraggable(), d.resizable && a.fn.resizable && b._makeResizable(), b._createButtons(d.buttons), b._isOpen = !1, a.fn.bgiframe && g.bgiframe()
		},
		_init: function() {
			this.options.autoOpen && this.open()
		},
		destroy: function() {
			var a = this;
			return a.overlay && a.overlay.destroy(), a.uiDialog.hide(), a.element.unbind(".dialog").removeData("dialog").removeClass("ui-dialog-content ui-widget-content").hide().appendTo("body"), a.uiDialog.remove(), a.originalTitle && a.element.attr("title", a.originalTitle), a
		},
		widget: function() {
			return this.uiDialog
		},
		close: function(b) {
			var c = this,
				d, e;
			if (!1 === c._trigger("beforeClose", b)) return;
			return c.overlay && c.overlay.destroy(), c.uiDialog.unbind("keypress.ui-dialog"), c._isOpen = !1, c.options.hide ? c.uiDialog.hide(c.options.hide, function() {
				c._trigger("close", b)
			}) : (c.uiDialog.hide(), c._trigger("close", b)), a.ui.dialog.overlay.resize(), c.options.modal && (d = 0, a(".ui-dialog").each(function() {
				this !== c.uiDialog[0] && (e = a(this).css("z-index"), isNaN(e) || (d = Math.max(d, e)))
			}), a.ui.dialog.maxZ = d), c
		},
		isOpen: function() {
			return this._isOpen
		},
		moveToTop: function(b, c) {
			var d = this,
				e = d.options,
				f;
			return e.modal && !b || !e.stack && !e.modal ? d._trigger("focus", c) : (e.zIndex > a.ui.dialog.maxZ && (a.ui.dialog.maxZ = e.zIndex), d.overlay && (a.ui.dialog.maxZ += 1, d.overlay.$el.css("z-index", a.ui.dialog.overlay.maxZ = a.ui.dialog.maxZ)), f = {
				scrollTop: d.element.scrollTop(),
				scrollLeft: d.element.scrollLeft()
			}, a.ui.dialog.maxZ += 1, d.uiDialog.css("z-index", a.ui.dialog.maxZ), d.element.attr(f), d._trigger("focus", c), d)
		},
		open: function() {
			if (this._isOpen) return;
			var b = this,
				c = b.options,
				d = b.uiDialog;
			return b.overlay = c.modal ? new a.ui.dialog.overlay(b) : null, b._size(), b._position(c.position), d.show(c.show), b.moveToTop(!0), c.modal && d.bind("keydown.ui-dialog", function(b) {
				if (b.keyCode !== a.ui.keyCode.TAB) return;
				var c = a(":tabbable", this),
					d = c.filter(":first"),
					e = c.filter(":last");
				if (b.target === e[0] && !b.shiftKey) return d.focus(1), !1;
				if (b.target === d[0] && b.shiftKey) return e.focus(1), !1
			}), a(b.element.find(":tabbable").get().concat(d.find(".ui-dialog-buttonpane :tabbable").get().concat(d.get()))).eq(0).focus(), b._isOpen = !0, b._trigger("open"), b
		},
		_createButtons: function(b) {
			var c = this,
				d = !1,
				e = a("<div></div>").addClass("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix"),
				f = a("<div></div>").addClass("ui-dialog-buttonset").appendTo(e);
			c.uiDialog.find(".ui-dialog-buttonpane").remove(), typeof b == "object" && b !== null && a.each(b, function() {
				return !(d = !0)
			}), d && (a.each(b, function(b, d) {
				d = a.isFunction(d) ? {
					click: d,
					text: b
				} : d;
				var e = a('<button type="button"></button>').click(function() {
					d.click.apply(c.element[0], arguments)
				}).appendTo(f);
				a.each(d, function(a, b) {
					if (a === "click") return;
					a in e ? e[a](b) : e.attr(a, b)
				}), a.fn.button && e.button()
			}), e.appendTo(c.uiDialog))
		},
		_makeDraggable: function() {
			function f(a) {
				return {
					position: a.position,
					offset: a.offset
				}
			}
			var b = this,
				c = b.options,
				d = a(document),
				e;
			b.uiDialog.draggable({
				cancel: ".ui-dialog-content, .ui-dialog-titlebar-close",
				handle: ".ui-dialog-titlebar",
				containment: "document",
				start: function(d, g) {
					e = c.height === "auto" ? "auto" : a(this).height(), a(this).height(a(this).height()).addClass("ui-dialog-dragging"), b._trigger("dragStart", d, f(g))
				},
				drag: function(a, c) {
					b._trigger("drag", a, f(c))
				},
				stop: function(g, h) {
					c.position = [h.position.left - d.scrollLeft(), h.position.top - d.scrollTop()], a(this).removeClass("ui-dialog-dragging").height(e), b._trigger("dragStop", g, f(h)), a.ui.dialog.overlay.resize()
				}
			})
		},
		_makeResizable: function(c) {
			function h(a) {
				return {
					originalPosition: a.originalPosition,
					originalSize: a.originalSize,
					position: a.position,
					size: a.size
				}
			}
			c = c === b ? this.options.resizable : c;
			var d = this,
				e = d.options,
				f = d.uiDialog.css("position"),
				g = typeof c == "string" ? c : "n,e,s,w,se,sw,ne,nw";
			d.uiDialog.resizable({
				cancel: ".ui-dialog-content",
				containment: "document",
				alsoResize: d.element,
				maxWidth: e.maxWidth,
				maxHeight: e.maxHeight,
				minWidth: e.minWidth,
				minHeight: d._minHeight(),
				handles: g,
				start: function(b, c) {
					a(this).addClass("ui-dialog-resizing"), d._trigger("resizeStart", b, h(c))
				},
				resize: function(a, b) {
					d._trigger("resize", a, h(b))
				},
				stop: function(b, c) {
					a(this).removeClass("ui-dialog-resizing"), e.height = a(this).height(), e.width = a(this).width(), d._trigger("resizeStop", b, h(c)), a.ui.dialog.overlay.resize()
				}
			}).css("position", f).find(".ui-resizable-se").addClass("ui-icon ui-icon-grip-diagonal-se")
		},
		_minHeight: function() {
			var a = this.options;
			return a.height === "auto" ? a.minHeight : Math.min(a.minHeight, a.height)
		},
		_position: function(b) {
			var c = [],
				d = [0, 0],
				e;
			if (b) {
				if (typeof b == "string" || typeof b == "object" && "0" in b) c = b.split ? b.split(" ") : [b[0], b[1]], c.length === 1 && (c[1] = c[0]), a.each(["left", "top"], function(a, b) {
					+c[a] === c[a] && (d[a] = c[a], c[a] = b)
				}), b = {
					my: c.join(" "),
					at: c.join(" "),
					offset: d.join(" ")
				};
				b = a.extend({}, a.ui.dialog.prototype.options.position, b)
			} else b = a.ui.dialog.prototype.options.position;
			e = this.uiDialog.is(":visible"), e || this.uiDialog.show(), this.uiDialog.css({
				top: 0,
				left: 0
			}).position(a.extend({
				of: window
			}, b)), e || this.uiDialog.hide()
		},
		_setOptions: function(b) {
			var c = this,
				f = {},
				g = !1;
			a.each(b, function(a, b) {
				c._setOption(a, b), a in d && (g = !0), a in e && (f[a] = b)
			}), g && this._size(), this.uiDialog.is(":data(resizable)") && this.uiDialog.resizable("option", f)
		},
		_setOption: function(b, d) {
			var e = this,
				f = e.uiDialog;
			switch (b) {
			case "beforeclose":
				b = "beforeClose";
				break;
			case "buttons":
				e._createButtons(d);
				break;
			case "closeText":
				e.uiDialogTitlebarCloseText.text("" + d);
				break;
			case "dialogClass":
				f.removeClass(e.options.dialogClass).addClass(c + d);
				break;
			case "disabled":
				d ? f.addClass("ui-dialog-disabled") : f.removeClass("ui-dialog-disabled");
				break;
			case "draggable":
				var g = f.is(":data(draggable)");
				g && !d && f.draggable("destroy"), !g && d && e._makeDraggable();
				break;
			case "position":
				e._position(d);
				break;
			case "resizable":
				var h = f.is(":data(resizable)");
				h && !d && f.resizable("destroy"), h && typeof d == "string" && f.resizable("option", "handles", d), !h && d !== !1 && e._makeResizable(d);
				break;
			case "title":
				a(".ui-dialog-title", e.uiDialogTitlebar).html("" + (d || "&#160;"))
			}
			a.Widget.prototype._setOption.apply(e, arguments)
		},
		_size: function() {
			var b = this.options,
				c, d, e = this.uiDialog.is(":visible");
			this.element.show().css({
				width: "auto",
				minHeight: 0,
				height: 0
			}), b.minWidth > b.width && (b.width = b.minWidth), c = this.uiDialog.css({
				height: "auto",
				width: b.width
			}).height(), d = Math.max(0, b.minHeight - c);
			if (b.height === "auto") if (a.support.minHeight) this.element.css({
				minHeight: d,
				height: "auto"
			});
			else {
				this.uiDialog.show();
				var f = this.element.css("height", "auto").height();
				e || this.uiDialog.hide(), this.element.height(Math.max(f, d))
			} else this.element.height(Math.max(b.height - c, 0));
			this.uiDialog.is(":data(resizable)") && this.uiDialog.resizable("option", "minHeight", this._minHeight())
		}
	}), a.extend(a.ui.dialog, {
		version: "1.8.23",
		uuid: 0,
		maxZ: 0,
		getTitleId: function(a) {
			var b = a.attr("id");
			return b || (this.uuid += 1, b = this.uuid), "ui-dialog-title-" + b
		},
		overlay: function(b) {
			this.$el = a.ui.dialog.overlay.create(b)
		}
	}), a.extend(a.ui.dialog.overlay, {
		instances: [],
		oldInstances: [],
		maxZ: 0,
		events: a.map("focus,mousedown,mouseup,keydown,keypress,click".split(","), function(a) {
			return a + ".dialog-overlay"
		}).join(" "),
		create: function(b) {
			this.instances.length === 0 && (setTimeout(function() {
				a.ui.dialog.overlay.instances.length && a(document).bind(a.ui.dialog.overlay.events, function(b) {
					if (a(b.target).zIndex() < a.ui.dialog.overlay.maxZ) return !1
				})
			}, 1), a(document).bind("keydown.dialog-overlay", function(c) {
				b.options.closeOnEscape && !c.isDefaultPrevented() && c.keyCode && c.keyCode === a.ui.keyCode.ESCAPE && (b.close(c), c.preventDefault())
			}), a(window).bind("resize.dialog-overlay", a.ui.dialog.overlay.resize));
			var c = (this.oldInstances.pop() || a("<div></div>").addClass("ui-widget-overlay")).appendTo(document.body).css({
				width: this.width(),
				height: this.height()
			});
			return a.fn.bgiframe && c.bgiframe(), this.instances.push(c), c
		},
		destroy: function(b) {
			var c = a.inArray(b, this.instances);
			c != -1 && this.oldInstances.push(this.instances.splice(c, 1)[0]), this.instances.length === 0 && a([document, window]).unbind(".dialog-overlay"), b.remove();
			var d = 0;
			a.each(this.instances, function() {
				d = Math.max(d, this.css("z-index"))
			}), this.maxZ = d
		},
		height: function() {
			var b, c;
			return a.browser.msie && a.browser.version < 7 ? (b = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight), c = Math.max(document.documentElement.offsetHeight, document.body.offsetHeight), b < c ? a(window).height() + "px" : b + "px") : a(document).height() + "px"
		},
		width: function() {
			var b, c;
			return a.browser.msie ? (b = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth), c = Math.max(document.documentElement.offsetWidth, document.body.offsetWidth), b < c ? a(window).width() + "px" : b + "px") : a(document).width() + "px"
		},
		resize: function() {
			var b = a([]);
			a.each(a.ui.dialog.overlay.instances, function() {
				b = b.add(this)
			}), b.css({
				width: 0,
				height: 0
			}).css({
				width: a.ui.dialog.overlay.width(),
				height: a.ui.dialog.overlay.height()
			})
		}
	}), a.extend(a.ui.dialog.overlay.prototype, {
		destroy: function() {
			a.ui.dialog.overlay.destroy(this.$el)
		}
	})
})(jQuery);;
(function(a, b) {
	var c = !1;
	a(document).mouseup(function(a) {
		c = !1
	}), a.widget("ui.mouse", {
		options: {
			cancel: ":input,option",
			distance: 1,
			delay: 0
		},
		_mouseInit: function() {
			var b = this;
			this.element.bind("mousedown." + this.widgetName, function(a) {
				return b._mouseDown(a)
			}).bind("click." + this.widgetName, function(c) {
				if (!0 === a.data(c.target, b.widgetName + ".preventClickEvent")) return a.removeData(c.target, b.widgetName + ".preventClickEvent"), c.stopImmediatePropagation(), !1
			}), this.started = !1
		},
		_mouseDestroy: function() {
			this.element.unbind("." + this.widgetName), this._mouseMoveDelegate && a(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate)
		},
		_mouseDown: function(b) {
			if (c) return;
			this._mouseStarted && this._mouseUp(b), this._mouseDownEvent = b;
			var d = this,
				e = b.which == 1,
				f = typeof this.options.cancel == "string" && b.target.nodeName ? a(b.target).closest(this.options.cancel).length : !1;
			if (!e || f || !this._mouseCapture(b)) return !0;
			this.mouseDelayMet = !this.options.delay, this.mouseDelayMet || (this._mouseDelayTimer = setTimeout(function() {
				d.mouseDelayMet = !0
			}, this.options.delay));
			if (this._mouseDistanceMet(b) && this._mouseDelayMet(b)) {
				this._mouseStarted = this._mouseStart(b) !== !1;
				if (!this._mouseStarted) return b.preventDefault(), !0
			}
			return !0 === a.data(b.target, this.widgetName + ".preventClickEvent") && a.removeData(b.target, this.widgetName + ".preventClickEvent"), this._mouseMoveDelegate = function(a) {
				return d._mouseMove(a)
			}, this._mouseUpDelegate = function(a) {
				return d._mouseUp(a)
			}, a(document).bind("mousemove." + this.widgetName, this._mouseMoveDelegate).bind("mouseup." + this.widgetName, this._mouseUpDelegate), b.preventDefault(), c = !0, !0
		},
		_mouseMove: function(b) {
			return !a.browser.msie || document.documentMode >= 9 || !! b.button ? this._mouseStarted ? (this._mouseDrag(b), b.preventDefault()) : (this._mouseDistanceMet(b) && this._mouseDelayMet(b) && (this._mouseStarted = this._mouseStart(this._mouseDownEvent, b) !== !1, this._mouseStarted ? this._mouseDrag(b) : this._mouseUp(b)), !this._mouseStarted) : this._mouseUp(b)
		},
		_mouseUp: function(b) {
			return a(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate), this._mouseStarted && (this._mouseStarted = !1, b.target == this._mouseDownEvent.target && a.data(b.target, this.widgetName + ".preventClickEvent", !0), this._mouseStop(b)), !1
		},
		_mouseDistanceMet: function(a) {
			return Math.max(Math.abs(this._mouseDownEvent.pageX - a.pageX), Math.abs(this._mouseDownEvent.pageY - a.pageY)) >= this.options.distance
		},
		_mouseDelayMet: function(a) {
			return this.mouseDelayMet
		},
		_mouseStart: function(a) {},
		_mouseDrag: function(a) {},
		_mouseStop: function(a) {},
		_mouseCapture: function(a) {
			return !0
		}
	})
})(jQuery);;
(function(a, b) {
	var c = 5;
	a.widget("ui.slider", a.ui.mouse, {
		widgetEventPrefix: "slide",
		options: {
			animate: !1,
			distance: 0,
			max: 100,
			min: 0,
			orientation: "horizontal",
			range: !1,
			step: 1,
			value: 0,
			values: null
		},
		_create: function() {
			var b = this,
				d = this.options,
				e = this.element.find(".ui-slider-handle").addClass("ui-state-default ui-corner-all"),
				f = "<a class='ui-slider-handle ui-state-default ui-corner-all' href='#'></a>",
				g = d.values && d.values.length || 1,
				h = [];
			this._keySliding = !1, this._mouseSliding = !1, this._animateOff = !0, this._handleIndex = null, this._detectOrientation(), this._mouseInit(), this.element.addClass("ui-slider ui-slider-" + this.orientation + " ui-widget" + " ui-widget-content" + " ui-corner-all" + (d.disabled ? " ui-slider-disabled ui-disabled" : "")), this.range = a([]), d.range && (d.range === !0 && (d.values || (d.values = [this._valueMin(), this._valueMin()]), d.values.length && d.values.length !== 2 && (d.values = [d.values[0], d.values[0]])), this.range = a("<div></div>").appendTo(this.element).addClass("ui-slider-range ui-widget-header" + (d.range === "min" || d.range === "max" ? " ui-slider-range-" + d.range : "")));
			for (var i = e.length; i < g; i += 1) h.push(f);
			this.handles = e.add(a(h.join("")).appendTo(b.element)), this.handle = this.handles.eq(0), this.handles.add(this.range).filter("a").click(function(a) {
				a.preventDefault()
			}).hover(function() {
				d.disabled || a(this).addClass("ui-state-hover")
			}, function() {
				a(this).removeClass("ui-state-hover")
			}).focus(function() {
				d.disabled ? a(this).blur() : (a(".ui-slider .ui-state-focus").removeClass("ui-state-focus"), a(this).addClass("ui-state-focus"))
			}).blur(function() {
				a(this).removeClass("ui-state-focus")
			}), this.handles.each(function(b) {
				a(this).data("index.ui-slider-handle", b)
			}), this.handles.keydown(function(d) {
				var e = a(this).data("index.ui-slider-handle"),
					f, g, h, i;
				if (b.options.disabled) return;
				switch (d.keyCode) {
				case a.ui.keyCode.HOME:
				case a.ui.keyCode.END:
				case a.ui.keyCode.PAGE_UP:
				case a.ui.keyCode.PAGE_DOWN:
				case a.ui.keyCode.UP:
				case a.ui.keyCode.RIGHT:
				case a.ui.keyCode.DOWN:
				case a.ui.keyCode.LEFT:
					d.preventDefault();
					if (!b._keySliding) {
						b._keySliding = !0, a(this).addClass("ui-state-active"), f = b._start(d, e);
						if (f === !1) return
					}
				}
				i = b.options.step, b.options.values && b.options.values.length ? g = h = b.values(e) : g = h = b.value();
				switch (d.keyCode) {
				case a.ui.keyCode.HOME:
					h = b._valueMin();
					break;
				case a.ui.keyCode.END:
					h = b._valueMax();
					break;
				case a.ui.keyCode.PAGE_UP:
					h = b._trimAlignValue(g + (b._valueMax() - b._valueMin()) / c);
					break;
				case a.ui.keyCode.PAGE_DOWN:
					h = b._trimAlignValue(g - (b._valueMax() - b._valueMin()) / c);
					break;
				case a.ui.keyCode.UP:
				case a.ui.keyCode.RIGHT:
					if (g === b._valueMax()) return;
					h = b._trimAlignValue(g + i);
					break;
				case a.ui.keyCode.DOWN:
				case a.ui.keyCode.LEFT:
					if (g === b._valueMin()) return;
					h = b._trimAlignValue(g - i)
				}
				b._slide(d, e, h)
			}).keyup(function(c) {
				var d = a(this).data("index.ui-slider-handle");
				b._keySliding && (b._keySliding = !1, b._stop(c, d), b._change(c, d), a(this).removeClass("ui-state-active"))
			}), this._refreshValue(), this._animateOff = !1
		},
		destroy: function() {
			return this.handles.remove(), this.range.remove(), this.element.removeClass("ui-slider ui-slider-horizontal ui-slider-vertical ui-slider-disabled ui-widget ui-widget-content ui-corner-all").removeData("slider").unbind(".slider"), this._mouseDestroy(), this
		},
		_mouseCapture: function(b) {
			var c = this.options,
				d, e, f, g, h, i, j, k, l;
			return c.disabled ? !1 : (this.elementSize = {
				width: this.element.outerWidth(),
				height: this.element.outerHeight()
			}, this.elementOffset = this.element.offset(), d = {
				x: b.pageX,
				y: b.pageY
			}, e = this._normValueFromMouse(d), f = this._valueMax() - this._valueMin() + 1, h = this, this.handles.each(function(b) {
				var c = Math.abs(e - h.values(b));
				f > c && (f = c, g = a(this), i = b)
			}), c.range === !0 && this.values(1) === c.min && (i += 1, g = a(this.handles[i])), j = this._start(b, i), j === !1 ? !1 : (this._mouseSliding = !0, h._handleIndex = i, g.addClass("ui-state-active").focus(), k = g.offset(), l = !a(b.target).parents().andSelf().is(".ui-slider-handle"), this._clickOffset = l ? {
				left: 0,
				top: 0
			} : {
				left: b.pageX - k.left - g.width() / 2,
				top: b.pageY - k.top - g.height() / 2 - (parseInt(g.css("borderTopWidth"), 10) || 0) - (parseInt(g.css("borderBottomWidth"), 10) || 0) + (parseInt(g.css("marginTop"), 10) || 0)
			}, this.handles.hasClass("ui-state-hover") || this._slide(b, i, e), this._animateOff = !0, !0))
		},
		_mouseStart: function(a) {
			return !0
		},
		_mouseDrag: function(a) {
			var b = {
				x: a.pageX,
				y: a.pageY
			},
				c = this._normValueFromMouse(b);
			return this._slide(a, this._handleIndex, c), !1
		},
		_mouseStop: function(a) {
			return this.handles.removeClass("ui-state-active"), this._mouseSliding = !1, this._stop(a, this._handleIndex), this._change(a, this._handleIndex), this._handleIndex = null, this._clickOffset = null, this._animateOff = !1, !1
		},
		_detectOrientation: function() {
			this.orientation = this.options.orientation === "vertical" ? "vertical" : "horizontal"
		},
		_normValueFromMouse: function(a) {
			var b, c, d, e, f;
			return this.orientation === "horizontal" ? (b = this.elementSize.width, c = a.x - this.elementOffset.left - (this._clickOffset ? this._clickOffset.left : 0)) : (b = this.elementSize.height, c = a.y - this.elementOffset.top - (this._clickOffset ? this._clickOffset.top : 0)), d = c / b, d > 1 && (d = 1), d < 0 && (d = 0), this.orientation === "vertical" && (d = 1 - d), e = this._valueMax() - this._valueMin(), f = this._valueMin() + d * e, this._trimAlignValue(f)
		},
		_start: function(a, b) {
			var c = {
				handle: this.handles[b],
				value: this.value()
			};
			return this.options.values && this.options.values.length && (c.value = this.values(b), c.values = this.values()), this._trigger("start", a, c)
		},
		_slide: function(a, b, c) {
			var d, e, f;
			this.options.values && this.options.values.length ? (d = this.values(b ? 0 : 1), this.options.values.length === 2 && this.options.range === !0 && (b === 0 && c > d || b === 1 && c < d) && (c = d), c !== this.values(b) && (e = this.values(), e[b] = c, f = this._trigger("slide", a, {
				handle: this.handles[b],
				value: c,
				values: e
			}), d = this.values(b ? 0 : 1), f !== !1 && this.values(b, c, !0))) : c !== this.value() && (f = this._trigger("slide", a, {
				handle: this.handles[b],
				value: c
			}), f !== !1 && this.value(c))
		},
		_stop: function(a, b) {
			var c = {
				handle: this.handles[b],
				value: this.value()
			};
			this.options.values && this.options.values.length && (c.value = this.values(b), c.values = this.values()), this._trigger("stop", a, c)
		},
		_change: function(a, b) {
			if (!this._keySliding && !this._mouseSliding) {
				var c = {
					handle: this.handles[b],
					value: this.value()
				};
				this.options.values && this.options.values.length && (c.value = this.values(b), c.values = this.values()), this._trigger("change", a, c)
			}
		},
		value: function(a) {
			if (arguments.length) {
				this.options.value = this._trimAlignValue(a), this._refreshValue(), this._change(null, 0);
				return
			}
			return this._value()
		},
		values: function(b, c) {
			var d, e, f;
			if (arguments.length > 1) {
				this.options.values[b] = this._trimAlignValue(c), this._refreshValue(), this._change(null, b);
				return
			}
			if (!arguments.length) return this._values();
			if (!a.isArray(arguments[0])) return this.options.values && this.options.values.length ? this._values(b) : this.value();
			d = this.options.values, e = arguments[0];
			for (f = 0; f < d.length; f += 1) d[f] = this._trimAlignValue(e[f]), this._change(null, f);
			this._refreshValue()
		},
		_setOption: function(b, c) {
			var d, e = 0;
			a.isArray(this.options.values) && (e = this.options.values.length), a.Widget.prototype._setOption.apply(this, arguments);
			switch (b) {
			case "disabled":
				c ? (this.handles.filter(".ui-state-focus").blur(), this.handles.removeClass("ui-state-hover"), this.handles.propAttr("disabled", !0), this.element.addClass("ui-disabled")) : (this.handles.propAttr("disabled", !1), this.element.removeClass("ui-disabled"));
				break;
			case "orientation":
				this._detectOrientation(), this.element.removeClass("ui-slider-horizontal ui-slider-vertical").addClass("ui-slider-" + this.orientation), this._refreshValue();
				break;
			case "value":
				this._animateOff = !0, this._refreshValue(), this._change(null, 0), this._animateOff = !1;
				break;
			case "values":
				this._animateOff = !0, this._refreshValue();
				for (d = 0; d < e; d += 1) this._change(null, d);
				this._animateOff = !1
			}
		},
		_value: function() {
			var a = this.options.value;
			return a = this._trimAlignValue(a), a
		},
		_values: function(a) {
			var b, c, d;
			if (arguments.length) return b = this.options.values[a], b = this._trimAlignValue(b), b;
			c = this.options.values.slice();
			for (d = 0; d < c.length; d += 1) c[d] = this._trimAlignValue(c[d]);
			return c
		},
		_trimAlignValue: function(a) {
			if (a <= this._valueMin()) return this._valueMin();
			if (a >= this._valueMax()) return this._valueMax();
			var b = this.options.step > 0 ? this.options.step : 1,
				c = (a - this._valueMin()) % b,
				d = a - c;
			return Math.abs(c) * 2 >= b && (d += c > 0 ? b : -b), parseFloat(d.toFixed(5))
		},
		_valueMin: function() {
			return this.options.min
		},
		_valueMax: function() {
			return this.options.max
		},
		_refreshValue: function() {
			var b = this.options.range,
				c = this.options,
				d = this,
				e = this._animateOff ? !1 : c.animate,
				f, g = {},
				h, i, j, k;
			this.options.values && this.options.values.length ? this.handles.each(function(b, i) {
				f = (d.values(b) - d._valueMin()) / (d._valueMax() - d._valueMin()) * 100, g[d.orientation === "horizontal" ? "left" : "bottom"] = f + "%", a(this).stop(1, 1)[e ? "animate" : "css"](g, c.animate), d.options.range === !0 && (d.orientation === "horizontal" ? (b === 0 && d.range.stop(1, 1)[e ? "animate" : "css"]({
					left: f + "%"
				}, c.animate), b === 1 && d.range[e ? "animate" : "css"]({
					width: f - h + "%"
				}, {
					queue: !1,
					duration: c.animate
				})) : (b === 0 && d.range.stop(1, 1)[e ? "animate" : "css"]({
					bottom: f + "%"
				}, c.animate), b === 1 && d.range[e ? "animate" : "css"]({
					height: f - h + "%"
				}, {
					queue: !1,
					duration: c.animate
				}))), h = f
			}) : (i = this.value(), j = this._valueMin(), k = this._valueMax(), f = k !== j ? (i - j) / (k - j) * 100 : 0, g[d.orientation === "horizontal" ? "left" : "bottom"] = f + "%", this.handle.stop(1, 1)[e ? "animate" : "css"](g, c.animate), b === "min" && this.orientation === "horizontal" && this.range.stop(1, 1)[e ? "animate" : "css"]({
				width: f + "%"
			}, c.animate), b === "max" && this.orientation === "horizontal" && this.range[e ? "animate" : "css"]({
				width: 100 - f + "%"
			}, {
				queue: !1,
				duration: c.animate
			}), b === "min" && this.orientation === "vertical" && this.range.stop(1, 1)[e ? "animate" : "css"]({
				height: f + "%"
			}, c.animate), b === "max" && this.orientation === "vertical" && this.range[e ? "animate" : "css"]({
				height: 100 - f + "%"
			}, {
				queue: !1,
				duration: c.animate
			}))
		}
	})
})(jQuery);;