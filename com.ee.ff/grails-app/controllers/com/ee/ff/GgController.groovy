package com.ee.ff

import org.springframework.dao.DataIntegrityViolationException

class GgController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [ggInstanceList: Gg.list(params), ggInstanceTotal: Gg.count()]
    }

    def create() {
        [ggInstance: new Gg(params)]
    }

    def save() {
        def ggInstance = new Gg(params)
        if (!ggInstance.save(flush: true)) {
            render(view: "create", model: [ggInstance: ggInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'gg.label', default: 'Gg'), ggInstance.id])
        redirect(action: "show", id: ggInstance.id)
    }

    def show(Long id) {
        def ggInstance = Gg.get(id)
        if (!ggInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gg.label', default: 'Gg'), id])
            redirect(action: "list")
            return
        }

        [ggInstance: ggInstance]
    }

    def edit(Long id) {
        def ggInstance = Gg.get(id)
        if (!ggInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gg.label', default: 'Gg'), id])
            redirect(action: "list")
            return
        }

        [ggInstance: ggInstance]
    }

    def update(Long id, Long version) {
        def ggInstance = Gg.get(id)
        if (!ggInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gg.label', default: 'Gg'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (ggInstance.version > version) {
                ggInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'gg.label', default: 'Gg')] as Object[],
                          "Another user has updated this Gg while you were editing")
                render(view: "edit", model: [ggInstance: ggInstance])
                return
            }
        }

        ggInstance.properties = params

        if (!ggInstance.save(flush: true)) {
            render(view: "edit", model: [ggInstance: ggInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'gg.label', default: 'Gg'), ggInstance.id])
        redirect(action: "show", id: ggInstance.id)
    }

    def delete(Long id) {
        def ggInstance = Gg.get(id)
        if (!ggInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gg.label', default: 'Gg'), id])
            redirect(action: "list")
            return
        }

        try {
            ggInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'gg.label', default: 'Gg'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'gg.label', default: 'Gg'), id])
            redirect(action: "show", id: id)
        }
    }
}
