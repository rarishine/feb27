package com.ee.ff



import org.junit.*
import grails.test.mixin.*

@TestFor(GgController)
@Mock(Gg)
class GgControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/gg/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.ggInstanceList.size() == 0
        assert model.ggInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.ggInstance != null
    }

    void testSave() {
        controller.save()

        assert model.ggInstance != null
        assert view == '/gg/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/gg/show/1'
        assert controller.flash.message != null
        assert Gg.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/gg/list'

        populateValidParams(params)
        def gg = new Gg(params)

        assert gg.save() != null

        params.id = gg.id

        def model = controller.show()

        assert model.ggInstance == gg
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/gg/list'

        populateValidParams(params)
        def gg = new Gg(params)

        assert gg.save() != null

        params.id = gg.id

        def model = controller.edit()

        assert model.ggInstance == gg
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/gg/list'

        response.reset()

        populateValidParams(params)
        def gg = new Gg(params)

        assert gg.save() != null

        // test invalid parameters in update
        params.id = gg.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/gg/edit"
        assert model.ggInstance != null

        gg.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/gg/show/$gg.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        gg.clearErrors()

        populateValidParams(params)
        params.id = gg.id
        params.version = -1
        controller.update()

        assert view == "/gg/edit"
        assert model.ggInstance != null
        assert model.ggInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/gg/list'

        response.reset()

        populateValidParams(params)
        def gg = new Gg(params)

        assert gg.save() != null
        assert Gg.count() == 1

        params.id = gg.id

        controller.delete()

        assert Gg.count() == 0
        assert Gg.get(gg.id) == null
        assert response.redirectedUrl == '/gg/list'
    }
}
